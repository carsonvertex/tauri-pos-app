// product-api.ts - MySQL Product API functions

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// MySQL Product API functions

// Get all products from MySQL
export const getAllProducts = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/products`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching products from MySQL:", error)
    throw error
  }
}

// Get product by ID from MySQL
export const getProductById = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product from MySQL:", error)
    throw error
  }
}

// Get products by status from MySQL
export const getProductsByStatus = async (status: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/status/${status}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching products by status from MySQL:", error)
    throw error
  }
}

// Get products by brand ID from MySQL
export const getProductsByBrandId = async (brandId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/brand/${brandId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching products by brand from MySQL:", error)
    throw error
  }
}

// Get products by SKU from MySQL
export const getProductsBySku = async (sku: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/sku/${sku}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching products by SKU from MySQL:", error)
    throw error
  }
}

// Get products by barcode from MySQL
export const getProductsByBarcode = async (barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/barcode/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching products by barcode from MySQL:", error)
    throw error
  }
}

// Health check for MySQL products endpoint
export const checkProductsHealth = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/health`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.text()
    return result
  } catch (error) {
    console.error("Error checking products health:", error)
    throw error
  }
}
