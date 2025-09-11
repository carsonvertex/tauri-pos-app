// localProductDescription-api.ts

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

// Local Product Description API functions

// Get all local product descriptions
export const getAllLocalProductDescriptions = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product descriptions:", error)
    throw error
  }
}

// Get local product description by product ID, site ID, and language ID
export const getLocalProductDescriptionById = async (productId: number, siteId: number, languageId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/${productId}/${siteId}/${languageId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product description:", error)
    throw error
  }
}

// Create new local product description
export const createLocalProductDescription = async (descriptionData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(descriptionData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success creating local product description")
    return result
  } catch (error) {
    console.error("Error creating local product description:", error)
    throw error
  }
}

// Update local product description by product ID, site ID, and language ID
export const updateLocalProductDescription = async (productId: number, siteId: number, languageId: number, descriptionData: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/${productId}/${siteId}/${languageId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(descriptionData),
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    console.log("Success updating local product description")
    return result
  } catch (error) {
    console.error("Error updating local product description:", error)
    throw error
  }
}

// Delete local product description by product ID, site ID, and language ID
export const deleteLocalProductDescription = async (productId: number, siteId: number, languageId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/${productId}/${siteId}/${languageId}`, {
      method: "DELETE",
    })

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    console.log("Success deleting local product description")
    return true
  } catch (error) {
    console.error("Error deleting local product description:", error)
    throw error
  }
}

// Get local product descriptions by product ID
export const getLocalProductDescriptionsByProductId = async (productId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/product/${productId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product descriptions by product ID:", error)
    throw error
  }
}

// Get local product descriptions by site ID
export const getLocalProductDescriptionsBySiteId = async (siteId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/site/${siteId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product descriptions by site ID:", error)
    throw error
  }
}

// Get local product descriptions by language ID
export const getLocalProductDescriptionsByLanguageId = async (languageId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/language/${languageId}`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result
  } catch (error) {
    console.error("Error fetching local product descriptions by language ID:", error)
    throw error
  }
}

// Get total count of local product descriptions
export const getLocalProductDescriptionsCount = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/local-product-descriptions/count`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return typeof result === 'object' && result.count !== undefined ? result.count : result
  } catch (error) {
    console.error("Error fetching local product descriptions count:", error)
    throw error
  }
}
