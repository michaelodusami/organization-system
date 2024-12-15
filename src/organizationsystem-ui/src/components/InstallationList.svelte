<script>
    import { onMount } from "svelte";
    import axios from "axios";
  
    let installations = [];
    let loading = true;
    let errorMessage = "";
  
    onMount(async () => {
      try {
        const response = await axios.get("http://localhost:8080/installations");
        installations = response.data;
        console.log(installations)
      } catch (error) {
        console.error("Error fetching installations:", error);
        errorMessage = "Failed to fetch installations. Please check the backend.";
      } finally {
        loading = false;
      }
    });
  </script>
  
  <main>
    <h1>Installations</h1>
    {#if loading}
      <p>Loading installations...</p>
    {:else if errorMessage}
      <p style="color: red;">{errorMessage}</p>
    {:else if installations.length === 0}
      <p>No installations found.</p>
    {:else}
      <ul>
        {#each installations as installation, index}
          <li>
            <a href={`/installation/${index}`}>
              {installation.repair} - {installation.repairStartDate} to {installation.repairEndDate}
            </a>
          </li>
        {/each}
      </ul>
    {/if}
  </main>
  