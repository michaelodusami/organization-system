
  

  <script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { page } from "$app/stores";
  
    let installation = null;
    let loading = true;
    let errorMessage = "";
    let planInput = "";
    let reviewInput = "";
    let fieldType = "";
    let fieldValue = "";
  
    const { id } = $page.params;
  
    onMount(async () => {
      try {
        const response = await axios.get(`http://localhost:8080/installations/${$page.params.id}`);
        installation = response.data;
      } catch (error) {
        console.error("Error fetching installation details:", error);
        errorMessage = "Failed to fetch installation details. Please check the backend.";
      } finally {
        loading = false;
      }
    });
  
    // Add a plan
    async function addPlan() {
      try {
        await axios.post(`http://localhost:8080/installations/${id}/plans`, planInput);
        installation.plan.push(planInput); // Update locally
        planInput = "";
      } catch (error) {
        console.error("Error adding plan:", error);
      }
    }
  
    // Add a weekly review
    async function addReview() {
      try {
        const payload = { review: reviewInput };
        const response = await axios.post(`http://localhost:8080/installations/${id}/weekly-reviews`, payload);
        installation.weeklyReviews.push(response.data); // Update locally
        reviewInput = "";
      } catch (error) {
        console.error("Error adding review:", error);
      }
    }
  
    // Add a field
    async function addField() {
      try {
        const payload = { type: fieldType, value: fieldValue };
        const response = await axios.post(`http://localhost:8080/installations/${id}/fields`, payload);
        installation.fieldsForTracking.push(response.data); // Update locally
        fieldType = "";
        fieldValue = "";
      } catch (error) {
        console.error("Error adding field:", error);
      }
    }
  </script>
  
  <main>
    {#if loading}
      <p>Loading installation details...</p>
    {:else if errorMessage}
      <p style="color: red;">{errorMessage}</p>
    {:else if installation}
      <h2>{installation.repair}</h2>
      <p>Start Date: {installation.repairStartDate}</p>
      <p>End Date: {installation.repairEndDate}</p>
      <p>Days Between: {installation.daysBetweenStartAndEnd}</p>
  
      <!-- Plans Section -->
      <h3>Plans</h3>
      <ul>
        {#each installation.plan as plan}
          <li>{plan}</li>
        {/each}
      </ul>
      <input type="text" bind:value={planInput} placeholder="Add a plan" />
      <button on:click={addPlan}>Add Plan</button>
  
      <!-- Weekly Reviews Section -->
      <h3>Weekly Reviews</h3>
      <ul>
        {#each installation.weeklyReviews as review}
          <li>{review.review}</li>
        {/each}
      </ul>
      <input type="text" bind:value={reviewInput} placeholder="Add a weekly review" />
      <button on:click={addReview}>Add Review</button>
  
      <!-- Fields Section -->
      <h3>Fields for Tracking</h3>
      <ul>
        {#each installation.fieldsForTracking as field}
          <li>{field.value} - {field.type}</li>
        {/each}
      </ul>
      <input type="text" bind:value={fieldType} placeholder="Field Type (e.g., TEXT)" />
      <input type="text" bind:value={fieldValue} placeholder="Field Value" />
      <button on:click={addField}>Add Field</button>
    {:else}
      <p>Installation not found.</p>
    {/if}
  </main>
  