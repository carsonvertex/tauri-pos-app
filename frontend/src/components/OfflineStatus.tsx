import React from 'react';
import { SyncStatusSummary } from '../types';
import { CloudOff, CloudSync, CheckCircle, Error, Warning } from '@mui/icons-material';

interface OfflineStatusProps {
  isOnline: boolean;
}

export const OfflineStatus: React.FC<OfflineStatusProps> = ({ 
  isOnline, 
}) => {



  const getStatusInfo = () => {
    if (!isOnline) {
      return {
        color: 'bg-gray-100 text-gray-800',
        icon: CloudOff,
        text: 'Offline'
      };
    }
 
    return {
      color: 'bg-green-100 text-green-800',
      icon: CheckCircle,
      text: 'Synced'
    };
  };

  const statusInfo = getStatusInfo();
  const StatusIcon = statusInfo.icon;

  return (
    <div className={`flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium ${statusInfo.color}`}>
      <StatusIcon className="w-4 h-4" />
      <span>{statusInfo.text}</span>
      
   
      
     
    </div>
  );
};
