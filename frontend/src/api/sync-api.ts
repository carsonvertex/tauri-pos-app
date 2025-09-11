// sync-api.ts - Synchronization API functions

import { getAllProducts } from './mysql-api/product-api'
import { getAllProductBarcodes } from './mysql-api/productBarcode-api'
import { getAllProductDescriptions } from './mysql-api/productDescription-api'
import { createLocalProduct } from './sqlite-api/localProduct-api'
import { createLocalProductBarcode } from './sqlite-api/localProductBarcode-api'
import { createLocalProductDescription } from './sqlite-api/localProductDescription-api'

export interface SyncResult {
  success: boolean
  message: string
  details: {
    products: {
      fetched: number
      synced: number
      errors: number
    }
    barcodes: {
      fetched: number
      synced: number
      errors: number
    }
    descriptions: {
      fetched: number
      synced: number
      errors: number
    }
  }
  errors: string[]
}

// Sync products from MySQL to SQLite
export const syncProducts = async (): Promise<SyncResult> => {
  const result: SyncResult = {
    success: true,
    message: 'Sync completed successfully',
    details: {
      products: { fetched: 0, synced: 0, errors: 0 },
      barcodes: { fetched: 0, synced: 0, errors: 0 },
      descriptions: { fetched: 0, synced: 0, errors: 0 }
    },
    errors: []
  }

  try {
    console.log('Starting product sync...')
    const products = await getAllProducts()
    result.details.products.fetched = products.length
    console.log(`Fetched ${products.length} products from MySQL`)

    for (const product of products) {
      try {
        await createLocalProduct(product)
        result.details.products.synced++
      } catch (error) {
        result.details.products.errors++
        result.errors.push(`Product ${product.id}: ${error instanceof Error ? error.message : 'Unknown error'}`)
        console.error(`Error syncing product ${product.id}:`, error)
      }
    }

    // Sync Product Barcodes
    console.log('Starting product barcodes sync...')
    const barcodes = await getAllProductBarcodes()
    result.details.barcodes.fetched = barcodes.length
    console.log(`Fetched ${barcodes.length} product barcodes from MySQL`)

    for (const barcode of barcodes) {
      try {
        await createLocalProductBarcode(barcode)
        result.details.barcodes.synced++
      } catch (error) {
        result.details.barcodes.errors++
        result.errors.push(`Barcode ${barcode.productId}/${barcode.barcode}: ${error instanceof Error ? error.message : 'Unknown error'}`)
        console.error(`Error syncing barcode ${barcode.productId}/${barcode.barcode}:`, error)
      }
    }

    // Sync Product Descriptions
    console.log('Starting product descriptions sync...')
    const descriptions = await getAllProductDescriptions()
    result.details.descriptions.fetched = descriptions.length
    console.log(`Fetched ${descriptions.length} product descriptions from MySQL`)

    for (const description of descriptions) {
      try {
        await createLocalProductDescription(description)
        result.details.descriptions.synced++
      } catch (error) {
        result.details.descriptions.errors++
        result.errors.push(`Description ${description.productId}/${description.siteId}/${description.languageId}: ${error instanceof Error ? error.message : 'Unknown error'}`)
        console.error(`Error syncing description ${description.productId}/${description.siteId}/${description.languageId}:`, error)
      }
    }

    // Determine overall success
    const totalErrors = result.details.products.errors + result.details.barcodes.errors + result.details.descriptions.errors
    if (totalErrors > 0) {
      result.success = false
      result.message = `Sync completed with ${totalErrors} errors`
    }

    console.log('Sync completed:', result)
    return result

  } catch (error) {
    result.success = false
    result.message = 'Sync failed due to connection or server error'
    result.errors.push(error instanceof Error ? error.message : 'Unknown error')
    console.error('Sync failed:', error)
    return result
  }
}

// Check if MySQL endpoints are available
export const checkMySQLAvailability = async (): Promise<boolean> => {
  try {
    // Try to fetch a small amount of data from each endpoint
    await Promise.all([
      getAllProducts(),
      getAllProductBarcodes(),
      getAllProductDescriptions()
    ])
    return true
  } catch (error) {
    console.error('MySQL endpoints not available:', error)
    return false
  }
}
