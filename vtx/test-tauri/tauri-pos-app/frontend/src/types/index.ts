export interface BackendStatus {
  running: boolean;
  port?: number;
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
  category: string;
}

export interface Order {
  id: number;
  orderNumber: string;
  orderDate: string;
  totalAmount: number;
  status: string;
  customerName: string;
  orderItems: OrderItem[];
}

export interface OrderItem {
  id: number;
  product: Product;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}

// Offline-specific types
export interface LocalProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  category: string;
  remoteId?: number;
  syncStatus: SyncStatus;
  lastSync?: string;
  createdAt: string;
  updatedAt: string;
}

export interface LocalOrder {
  id: number;
  orderNumber: string;
  total: number;
  customerName: string;
  customerEmail: string;
  status: OrderStatus;
  remoteId?: number;
  syncStatus: SyncStatus;
  lastSync?: string;
  createdAt: string;
  updatedAt: string;
  items: LocalOrderItem[];
}

export interface LocalOrderItem {
  id: number;
  order: LocalOrder;
  product: LocalProduct;
  quantity: number;
  price: number;
  remoteId?: number;
  syncStatus: SyncStatus;
  lastSync?: string;
  createdAt: string;
  updatedAt: string;
}

export const SyncStatus = {
  PENDING: 'PENDING',
  SYNCING: 'SYNCING',
  SYNCED: 'SYNCED',
  FAILED: 'FAILED',
  CONFLICT: 'CONFLICT'
} as const;

export type SyncStatus = typeof SyncStatus[keyof typeof SyncStatus];

export const OrderStatus = {
  PENDING: 'PENDING',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
  REFUNDED: 'REFUNDED'
} as const;

export type OrderStatus = typeof OrderStatus[keyof typeof OrderStatus];

export interface SyncStatusSummary {
  pendingProducts: number;
  pendingOrders: number;
  failedProducts: number;
  failedOrders: number;
  syncedProducts: number;
  syncedOrders: number;
  totalPending: number;
  totalFailed: number;
  totalSynced: number;
}