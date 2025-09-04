// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::process::{Command, Stdio};
use std::sync::{Arc, Mutex};
use std::thread;
use tauri::{Manager, State};
use serde::{Deserialize, Serialize};

#[derive(Default)]
struct BackendState {
    process: Mutex<Option<std::process::Child>>,
}

#[derive(Serialize, Deserialize)]
struct BackendStatus {
    running: bool,
    port: Option<u16>,
}

#[tauri::command]
async fn start_backend(state: State<'_, BackendState>) -> Result<BackendStatus, String> {
    let mut process_guard = state.process.lock().unwrap();
    
    if process_guard.is_some() {
        return Ok(BackendStatus {
            running: true,
            port: Some(8080),
        });
    }

    // Start Spring Boot backend
    let child = Command::new("java")
        .args(&["-jar", "backend/target/tauri-pos-app-0.1.0.jar"])
        .stdout(Stdio::piped())
        .stderr(Stdio::piped())
        .spawn()
        .map_err(|e| format!("Failed to start backend: {}", e))?;

    *process_guard = Some(child);
    
    Ok(BackendStatus {
        running: true,
        port: Some(8080),
    })
}

#[tauri::command]
async fn stop_backend(state: State<'_, BackendState>) -> Result<BackendStatus, String> {
    let mut process_guard = state.process.lock().unwrap();
    
    if let Some(mut child) = process_guard.take() {
        child.kill().map_err(|e| format!("Failed to stop backend: {}", e))?;
    }
    
    Ok(BackendStatus {
        running: false,
        port: None,
    })
}

#[tauri::command]
async fn get_backend_status(state: State<'_, BackendState>) -> Result<BackendStatus, String> {
    let process_guard = state.process.lock().unwrap();
    
    Ok(BackendStatus {
        running: process_guard.is_some(),
        port: if process_guard.is_some() { Some(8080) } else { None },
    })
}

fn main() {
    tauri::Builder::default()
        .manage(BackendState::default())
        .invoke_handler(tauri::generate_handler![
            start_backend,
            stop_backend,
            get_backend_status
        ])
        .setup(|app| {
            // Start backend automatically when app starts
            let app_handle = app.handle();
            thread::spawn(move || {
                // Wait a bit for the frontend to be ready
                thread::sleep(std::time::Duration::from_secs(2));
                let _ = app_handle.emit_all("backend-starting", ());
            });
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
