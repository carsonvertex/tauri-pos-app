import React from 'react';
import { SyncStatusSummary } from '../types';
import { CloudOff, CloudSync, CheckCircle, Error, Warning } from '@mui/icons-material';

interface OfflineStatusProps {
  isOnline: boolean;
  syncStatus: SyncStatusSummary | null;
  onForceSync: () => Promise<boolean>;
}

export const OfflineStatus: React.FC<OfflineStatusProps> = ({ 
  isOnline, 
  syncStatus, 
  onForceSync 
}) => {
  const [isSyncing, setIsSyncing] = React.useState(false);

  const handleForceSync = async () => {
    setIsSyncing(true);
    try {
      await onForceSync();
    } finally {
      setIsSyncing(false);
    }
  };

  const getStatusInfo = () => {
    if (!isOnline) {
      return {
        color: 'bg-gray-100 text-gray-800',
        icon: CloudOff,
        text: 'Offline'
      };
    }
    if (!syncStatus) {
      return {
        color: 'bg-yellow-100 text-yellow-800',
        icon: Warning,
        text: 'Checking...'
      };
    }
    if (syncStatus.totalPending > 0) {
      return {
        color: 'bg-orange-100 text-orange-800',
        icon: CloudSync,
        text: `${syncStatus.totalPending} Pending`
      };
    }
    if (syncStatus.totalFailed > 0) {
      return {
        color: 'bg-red-100 text-red-800',
        icon: Error,
        text: `${syncStatus.totalFailed} Failed`
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
      
      {/* Detailed Status */}
      {syncStatus && (
        <span className="text-xs opacity-75 ml-1">
          ({syncStatus.totalSynced} synced)
        </span>
      )}
      
      {/* Sync Button */}
      {isOnline && syncStatus && (syncStatus.totalPending > 0 || syncStatus.totalFailed > 0) && (
        <button
          onClick={handleForceSync}
          disabled={isSyncing}
          className="ml-2 px-2 py-1 text-xs bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50 transition-colors"
        >
          {isSyncing ? 'Syncing...' : 'Sync Now'}
        </button>
      )}
    </div>
  );
};
