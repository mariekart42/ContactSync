<template>
  <div class="container">
    <h1>Principals</h1>





    <!-- Principal Data -->
    <div v-if="principalData.length > 0">
      <h2 class="mb-2 font-bold">Principal Data</h2>
      <div style="overflow-x: auto;">
        <table border="1" cellpadding="6" cellspacing="0"
               style="border-collapse: collapse; width: 100%;  min-width: 600px;">
        <thead style="background-color: #f2f2f2;">
        <tr>
          <th>Sync</th>
          <th>syncprincipalid</th>
<!--          <th>syncuserid</th>-->
          <th>syncdevice_id</th>
          <th>principal_syncresult</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in principalData" :key="index">
          <td>
            <button @click="syncPrincipal(item.syncprincipalid)">
              sync
            </button>
          </td>
          <td>{{ item.syncprincipalid }}</td>
<!--          <td>{{ item.syncuserid }}</td>-->
          <td>{{ item.syncdevice_id }}</td>
          <td :style="{ color: item.principal_syncresult !== 'ok' ? 'red' : 'green' }">
            {{ item.principal_syncresult }}
          </td>
        </tr>
        </tbody>
      </table>
      </div>

<br><br>
      <!-- Contact Data -->
      <div v-if="contactData.length > 0">
        <h2 class="mb-2 font-bold">Synced Contacts</h2>
        <div style="overflow-x: auto;"> <!-- scroll container -->
          <table border="1" cellpadding="6" cellspacing="0"
                 style="border-collapse: collapse; width: 100%;  min-width: 600px;">
            <thead style="background-color: #f2f2f2;">
            <tr>
              <th style="word-break: break-word">sync abonnement id</th>
              <th >guid</th>
              <th >luid</th>
              <th>abostart</th>
              <th>aboende</th>
              <th>synced</th>
              <th>changed</th>
              <th>to_external</th>
              <th>avoidfields</th>
              <th>device</th>
              <th>devicespecifics</th>
              <th>abo_syncresult</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in contactData" :key="index">
              <td style="white-space: nowrap">{{ item.syncabonnementid }}</td>
              <td @click="copyText(item.guid, $event)" :title="tooltipText" style="cursor: pointer; word-break: break-all">{{ item.guid }}</td>
              <td @click="copyText(item.luid, $event)" :title="tooltipText" style="cursor: pointer; word-break: break-all">{{ item.luid }}</td>
              <td style="white-space: nowrap">{{ item.abostart }}</td>
              <td style="white-space: nowrap">{{ item.aboende }}</td>
              <td style="white-space: nowrap">{{ item.synced }}</td>
              <td style="white-space: nowrap">{{ item.changed }}</td>
              <td style="max-width: 150px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"
                  :title="item.to_external">{{ item.to_external }}</td>
              <td>{{ item.avoidfields }}</td>
              <td>{{ item.device }}</td>
              <td>{{ item.devicespecifics }}</td>
              <td>
                <div v-if="item.abo_syncresult === 'ok'"
                     style="color: green"
                     :title="item.abo_syncresult">
                  {{ item.abo_syncresult }}
                </div>
                <div v-else
                     style="max-width: 200px;  color: red; word-break: break-all"
                     :title="item.abo_syncresult">
                  {{ item.abo_syncresult }}
                 </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div v-else-if="errorMessage !== null">
        <p :style="{ color: 'red' }">Error while syncing Contact Data: {{ errorMessage }}</p>
      </div>



    </div>
    <div v-else-if="errorMessage !== null">
      <p :style="{ color: 'red' }">Error while fetching Principal Data: {{ errorMessage }}</p>
    </div>

















    <!--    <div>-->
<!--      <div v-if="contactData.length === 0">-->
<!--        Principal has no data-->
<!--      </div>-->

<!--      <div v-else>-->
<!--        <div-->
<!--            v-for="(map, index) in contactData"-->
<!--            :key="index"-->
<!--            class="map-container"-->
<!--        >-->
<!--          <h3>Eintrag {{ index + 1 }}</h3>-->
<!--          <ul>-->
<!--            <li v-for="(value, key) in map" :key="key">-->
<!--              <strong>{{ key }}:</strong> {{ value }}-->
<!--            </li>-->
<!--          </ul>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

<!--    <input v-model="message" placeholder="Type something..." />-->
<!--    <button @click="sendMessage">Send</button>-->
<!--    <p v-if="response">Backend response: {{ response }}</p>-->
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'


const message = ref('')
const response = ref('')

const principalData = ref([]); // store data from backend on load

const selectedItem = ref(null)
const contactData = ref([])
const errorMessage = ref(null)

const tooltipText = ref('Click to copy')

function copyText(text, event) {
  navigator.clipboard.writeText(text)
      .then(() => {
        tooltipText.value = 'Copied!'          // show tooltip
        setTimeout(() => tooltipText.value = 'Click to copy', 1500) // reset after 1.5s
      })
      .catch(() => {
        tooltipText.value = 'Copy failed'
        setTimeout(() => tooltipText.value = 'Click to copy', 1500)
      })
}


// Fetch data on page load
onMounted(async () => {
  try {
    // principalData.value = null;
    const res = await fetch('http://localhost:8080/principals')
    // replace with your backend endpoint
    if (!res.ok) throw new Error(`Error ${res.status}`)

    const data = await res.json();

    if (!data || data.length === 0)
    {
      errorMessage.value = "Could not fetch principal data"
      principalData.value = []
    }
    else {
      errorMessage.value = null
      principalData.value = data
    }



    // principalData.value = null;
    // errorMessage.value = "Could not load principal data."
    // console.log(principalData)
    // console.log(principalData.value)
  } catch (err) {
    console.error('Error fetching initial data:', err)
    principalData.value = [];
    errorMessage.value = "Could not load principal data."
  }
})



// syncs all contacts from principal, returns contact data of all updated contacts
async function syncPrincipal(item)
{
  selectedItem.value = item
  contactData.value = []

  try {
    const res = await fetch(`http://localhost:8080/getContactDataFromPrincipalId?principalId=${encodeURIComponent(item)}`)

    if (!res.ok)
      throw new Error(`Server error: ${res.status} ${res.statusText}`);

    const data = await res.json()

    if (!data || data.length === 0)
    {
      errorMessage.value = "Principal has no Contacts to sync"
      contactData.value = []
    }
    else {
      errorMessage.value = null
      contactData.value = data
    }
  } catch (err) {
    console.error('Error fetching contact data:', err)
    errorMessage.value = "Could not load contact data."
  }
}


</script>

<style>
.container {
  font-family: Arial, sans-serif;

  text-align: left;
  margin: 50px auto;
  width: 400px;
  display: table-footer-group;
  flex-direction: column;
  gap: 10px;
}

input {
  padding: 8px;
  font-size: 16px;
}

button {
  padding: 8px 12px;
  font-size: 16px;
  cursor: pointer;
}

p {
  margin-top: 20px;
  color: green;
}
</style>
