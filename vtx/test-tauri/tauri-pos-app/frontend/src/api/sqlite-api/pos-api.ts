// apiService.ts

export const API_BASE_URL = `http://${import.meta.env.VITE_API_HOST || 'localhost:8080'}/api`

//soleUser
export const getSoleUsers = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/users`)

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`)
    }

    const result = await response.json()
    return result // Return the fetched result
  } catch (error) {
    console.error("Error fetching data:", error)
    throw error // Rethrow the error for further handling
  }
}

 
export const updateTalentLevelBySoleUserId = async (
  soleUserId: string,
  values: any
) => {
  try {
    console.log("api", soleUserId, values)
    const response = await fetch(
      `${API_BASE_URL}/sole-users/talent-level/${soleUserId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(values),
      }
    )
    const result = await response.json()
    if (response.ok) {
      console.log("Success Update Talent Level")
      return result
    }
  } catch (error) {
    console.log("Error Updating Talent Level", error)
  }
}
