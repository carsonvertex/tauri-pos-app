// localProductBarcode-api.ts

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// Local Product Barcode API functions

// Get all local product barcodes
export const getAllLocalProductBarcodes = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product barcodes:", error)
    throw error
  }
}

// Get local product barcode by product ID and barcode
export const getLocalProductBarcodeById = async (productId: number, barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/${productId}/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product barcode:", error)
    throw error
  }
}

// Create new local product barcode
export const createLocalProductBarcode = async (barcodeData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(barcodeData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success creating local product barcode")
    return result
  } catch (error) {
    console.error("Error creating local product barcode:", error)
    throw error
  }
}

// Update local product barcode by product ID and barcode
export const updateLocalProductBarcode = async (productId: number, barcode: string, barcodeData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/${productId}/${barcode}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(barcodeData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success updating local product barcode")
    return result
  } catch (error) {
    console.error("Error updating local product barcode:", error)
    throw error
  }
}

// Delete local product barcode by product ID and barcode
export const deleteLocalProductBarcode = async (productId: number, barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/${productId}/${barcode}`, {
      method: "DELETE",
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    console.log("Success deleting local product barcode")
    return true
  } catch (error) {
    console.error("Error deleting local product barcode:", error)
    throw error
  }
}

// Get local product barcodes by product ID
export const getLocalProductBarcodesByProductId = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/product/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product barcodes by product ID:", error)
    throw error
  }
}

// Get local product barcodes by barcode
export const getLocalProductBarcodesByBarcode = async (barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/barcode/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product barcodes by barcode:", error)
    throw error
  }
}

// Get local product barcodes by status
export const getLocalProductBarcodesByStatus = async (status: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/status/${status}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product barcodes by status:", error)
    throw error
  }
}

// Get total count of local product barcodes
export const getLocalProductBarcodesCount = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-barcodes/count`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return typeof result === 'object' && result.count !== undefined ? result.count : result
  } catch (error) {
    console.error("Error fetching local product barcodes count:", error)
    throw error
  }
}
