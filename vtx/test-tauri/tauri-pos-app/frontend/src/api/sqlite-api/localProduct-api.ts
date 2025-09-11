// localProduct-api.ts

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// Local Product API functions

// Get all local products
export const getAllLocalProducts = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local products:", error)
    throw error
  }
}

// Get local product by ID
export const getLocalProductById = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product:", error)
    throw error
  }
}

// Create new local product
export const createLocalProduct = async (productData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(productData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success creating local product")
    return result
  } catch (error) {
    console.error("Error creating local product:", error)
    throw error
  }
}

// Update local product by ID
export const updateLocalProduct = async (productId: number, productData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/${productId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(productData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success updating local product")
    return result
  } catch (error) {
    console.error("Error updating local product:", error)
    throw error
  }
}

// Delete local product by ID
export const deleteLocalProduct = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/${productId}`, {
      method: "DELETE",
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    console.log("Success deleting local product")
    return true
  } catch (error) {
    console.error("Error deleting local product:", error)
    throw error
  }
}

// Get local products by status
export const getLocalProductsByStatus = async (status: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/status/${status}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local products by status:", error)
    throw error
  }
}

// Get local products by SKU
export const getLocalProductsBySku = async (sku: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/sku/${sku}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local products by SKU:", error)
    throw error
  }
}

// Get local products by barcode
export const getLocalProductsByBarcode = async (barcode: string) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/barcode/${barcode}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local products by barcode:", error)
    throw error
  }
}

// Get total count of local products
export const getLocalProductsCount = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-products/count`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return typeof result === 'object' && result.count !== undefined ? result.count : result
  } catch (error) {
    console.error("Error fetching local products count:", error)
    throw error
  }
}
