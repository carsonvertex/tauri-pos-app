// productDescription-api.ts - MySQL Product Description API functions

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// MySQL Product Description API functions

// Get all product descriptions from MySQL
export const getAllProductDescriptions = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product descriptions from MySQL:", error)
    throw error
  }
}

// Get product description by composite key from MySQL
export const getProductDescriptionById = async (productId: number, siteId: number, languageId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions/${productId}/${siteId}/${languageId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product description from MySQL:", error)
    throw error
  }
}

// Get product descriptions by product ID from MySQL
export const getProductDescriptionsByProductId = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions/product/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product descriptions by product ID from MySQL:", error)
    throw error
  }
}

// Get product descriptions by site ID from MySQL
export const getProductDescriptionsBySiteId = async (siteId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions/site/${siteId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product descriptions by site ID from MySQL:", error)
    throw error
  }
}

// Get product descriptions by language ID from MySQL
export const getProductDescriptionsByLanguageId = async (languageId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions/language/${languageId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching product descriptions by language ID from MySQL:", error)
    throw error
  }
}

// Health check for MySQL product descriptions endpoint
export const checkProductDescriptionsHealth = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/product-descriptions/health`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.text()
    return result
  } catch (error) {
    console.error("Error checking product descriptions health:", error)
    throw error
  }
}
