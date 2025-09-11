// productBarcode-api.ts - MySQL Product Barcode API functions

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// MySQL Product Barcode API functions

// Get all product barcodes from MySQL
export const getAllProductBarcodes = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product barcodes from MySQL:", error)
    throw error
  }
}

// Get product barcode by composite key from MySQL
export const getProductBarcodeById = async (productId: number, barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes/${productId}/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product barcode from MySQL:", error)
    throw error
  }
}

// Get product barcodes by product ID from MySQL
export const getProductBarcodesByProductId = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes/product/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product barcodes by product ID from MySQL:", error)
    throw error
  }
}

// Get product barcodes by barcode from MySQL
export const getProductBarcodesByBarcode = async (barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes/barcode/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product barcodes by barcode from MySQL:", error)
    throw error
  }
}

// Get product barcodes by status from MySQL
export const getProductBarcodesByStatus = async (status: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes/status/${status}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product barcodes by status from MySQL:", error)
    throw error
  }
}

// Health check for MySQL product barcodes endpoint
export const checkProductBarcodesHealth = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-barcodes/health`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.text()
    return result
  } catch (error) {
    console.error("Error checking product barcodes health:", error)
    throw error
  }
}
