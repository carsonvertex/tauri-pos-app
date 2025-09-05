import React from 'react';
import { SyncStatusSummary } from '../types';

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

  const getStatusColor = () => {
    if (!isOnline) return 'bg-gray-500';
    if (!syncStatus) return 'bg-yellow-500';
    if (syncStatus.totalPending > 0) return 'bg-orange-500';
    if (syncStatus.totalFailed > 0) return 'bg-red-500';
    return 'bg-green-500';
  };

  const getStatusText = () => {
    if (!isOnline) return 'Offline';
    if (!syncStatus) return 'Checking...';
    if (syncStatus.totalPending > 0) return `${syncStatus.totalPending} Pending`;
    if (syncStatus.totalFailed > 0) return `${syncStatus.totalFailed} Failed`;
    return 'Synced';
  };

  return (
    <div className="flex items-center space-x-2 p-2 bg-gray-100 rounded-lg">
      {/* Status Indicator */}
      <div className={`w-3 h-3 rounded-full ${getStatusColor()}`}></div>
      
      {/* Status Text */}
      <span className="text-sm font-medium text-gray-700">
        {getStatusText()}
      </span>
      
      {/* Sync Button */}
      {isOnline && syncStatus && (syncStatus.totalPending > 0 || syncStatus.totalFailed > 0) && (
        <button
          onClick={handleForceSync}
          disabled={isSyncing}
          className="px-2 py-1 text-xs bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50"
        >
          {isSyncing ? 'Syncing...' : 'Sync Now'}
        </button>
      )}
      
      {/* Detailed Status */}
      {syncStatus && (
        <div className="text-xs text-gray-500">
          {syncStatus.totalSynced} synced
        </div>
      )}
    </div>
  );
};
