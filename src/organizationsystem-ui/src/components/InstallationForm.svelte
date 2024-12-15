<script>
    import axios from "axios";
  
    let repair = "";
    let repairStartDate = "";
    let repairEndDate = "";
  
    async function createInstallation() {
      try {
        const payload = { repair, repairStartDate, repairEndDate };
        await axios.post("http://localhost:8080/installations", payload);
        alert("Installation created!");
      } catch (error) {
        console.error("Error creating installation:", error);
      }
    }
  </script>
  
  <form on:submit|preventDefault={createInstallation}>
    <label>
      Repair:
      <input type="text" bind:value={repair} required />
    </label>
    <label>
      Start Date:
      <input type="date" bind:value={repairStartDate} required />
    </label>
    <label>
      End Date:
      <input type="date" bind:value={repairEndDate} required />
    </label>
    <button type="submit">Create Installation</button>
  </form>
  