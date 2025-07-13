<script setup>

import {computed, inject, onMounted, ref} from "vue";
import TimetableModalView from "@/presentations/components/timetable/TimetableModalView.vue";
import {TimetableService} from "@/services/timetable.service";
import {DentistsService} from "@/services/dentists.service";

/*global defineProps*/

const data = defineProps({
  dentistId: String,
  dentistName: String,
  dentistSpecialization: String,
  procedureId: String,
  procedureName: String,
});

const alert = inject('alert')

const selectedDate = ref();
const selectedTimeInterval = ref();

const appointmentWindowIsOpen = ref(false)
const timetable = ref([])
const timetableLabels = ref([])

const timetableRows = 9
const timetableColumn = 14

const createTimeSlot = () => ({
  day: '',
  type: '',
  timeRanges: '',
  holidayName: '',
  isLunch: false,
  isAvailable: true
});

const timetableMatrix = ref(
    Array.from({ length: timetableColumn }, () =>
        Array.from({ length: timetableRows }, createTimeSlot)
    )
);

onMounted( async () => {
  try {
    const TimetableServiceResponse = await TimetableService.getTimetable(data.dentistId);
    await DentistsService.getDentistById(data.dentistId)

    timetable.value = TimetableServiceResponse.days;

    for (const day in timetable.value) {
      timetableLabels.value[day] = dateConverter(timetable.value.at(day).date) + "\n" + timetable.value[day].dayOfWeek;
    }

    getTimetable()

  }
  catch(error) {
    console.log("Error at AppointmentTimetable on mounted: " + error);
  }
});

const dateConverter = (date) => {
  const [year, month, day] = date.split("-");
  return `${day}.${month}.${year}`;
};

const getTimetable = () => {

  for (const column in timetableMatrix.value) {

    for (const row in timetableMatrix.value[column]) {

      timetableMatrix.value[column][row].day  = dateConverter(timetable.value.at(column).date)

      if (timetable.value.at(column).dayType === 'HOLIDAY') {
        timetableMatrix.value[column][row].type = 'HOLIDAY'
        timetableMatrix.value[column][row].holidayName = timetable.value.at(column).holidayResponse.name
      }

      else {
        timetableMatrix.value[column][row].type = 'WORKDAY'
        timetableMatrix.value[column][row].timeRanges = getTimeInterval(
            timetable.value.at(column).workDayResponse.timeSlots[row].beginTime,
            timetable.value.at(column).workDayResponse.timeSlots[row].endTime
        )
        timetableMatrix.value[column][row].isLunch = timetable.value.at(column).workDayResponse.timeSlots[row].isLunch
        timetableMatrix.value[column][row].isAvailable = timetable.value.at(column).workDayResponse.timeSlots[row].isAvailable
      }
    }
  }

  console.log(timetableMatrix.value)
};

const getTimeInterval = (beginTime, endTime) => {
  const [hh, mm, ss] = beginTime.split(":")
  const [hours, minutes, seconds] = endTime.split(":")
  return `${hh}:${mm}-${hours}:${minutes}`;
}

const getCellValue = (row, col) => {
  return timetableMatrix.value[col][row].timeRanges
}

const getCellAvailableValue = (row, col) => {
  return timetableMatrix.value[col][row].isAvailable
}

const isCellFree = (row, col) => {
  return timetableMatrix.value[col][row].isAvailable
}

const isWeekend = (col) => {
  return timetableMatrix.value[col][0].type === 'HOLIDAY'
}

const getDateForColumn = (col) => {
  return timetableMatrix.value[col][0].day
}

const getHolidayName = (col) => {
  if (isWeekend(col)) {
    return timetableMatrix.value[col][0].holidayName || "HOLIDAY";
  }
  return null;
}

const onOpenModal = (row, col) => {

  if (data.isPreview) {
    alert('On this page you can only see the timetable of dentists.')
  } else if (isCellFree(row, col)) {
    openModalWindow(row, col)
  } else if (row === 6){
    alert("There is no appointment at this time.")
  } else {
    alert("You cannot make an appointment for a time that is already occupied.")
  }
}
const openModalWindow = (row, col) => {

  selectedDate.value = timetableMatrix.value[col][row].day
  selectedTimeInterval.value = timetableMatrix.value[col][row].timeRanges

  appointmentWindowIsOpen.value = true
}

</script>

<template>



  <div class="custom-timetable">
    <div
        v-for="(col, columnIndex) in timetable.length"
        :key="columnIndex"
        class="timetable-column"
    >

      <!-- Праздничный / выходной день -->
      <div
          v-if="isWeekend(columnIndex)"
          class="custom-timetable-cell holiday-full-cell"
      >
        <div class="holiday-full-content">
          <div class="holiday-title-large">
            {{ timetableLabels[columnIndex] }}
          </div>
          <div class="holiday-name-large">
            {{ getHolidayName(columnIndex) }}
          </div>
        </div>
      </div>

      <!-- Рабочий день -->
      <template v-else>
        <div
            v-for="row in 10"
            :key="row"
            class="custom-timetable-cell"
        >
          <div
              v-if="row === 1"
              class="custom-timetable-cell-content-title"
          >
            {{ timetableLabels[columnIndex] }}
          </div>
          <div
              v-else-if="row === 6"
              class="custom-timetable-cell-content busy"
          >
            {{ getCellValue(row - 2, columnIndex) }}
          </div>
          <div
              v-else
              class="custom-timetable-cell-content"
              :class="{
            free: getCellAvailableValue(row - 2, columnIndex),
            busy: !getCellAvailableValue(row - 2, columnIndex)
          }"
              @click="onOpenModal(row - 2, columnIndex)"
          >
            {{ getCellValue(row - 2, columnIndex) }}
          </div>
        </div>
      </template>

    </div>
  </div>

  <TimetableModalView
      v-if="appointmentWindowIsOpen"
      :client-name="`Client Name`"
      :dentist-id="dentistId"
      :dentist-name="dentistName"
      :dentist-specialization="dentistSpecialization"
      :procedure-id="procedureId"
      :procedure-name="procedureName"
      :date="selectedDate"
      :time="selectedTimeInterval"
      @close="appointmentWindowIsOpen = false"
  />

</template>

<style scoped>

@import "@/assets/css/timetable/time-table.css";

.custom-timetable {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 10px;
  padding: 20px;
  background-color: #f7f9fc;
  border-radius: 12px;
}

.custom-timetable-cell {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  transition: transform 0.2s;
}

.custom-timetable-cell:hover {
  transform: scale(1.02);
}

.custom-timetable-cell-content-title {
  background-color: var(--dark-primary-color);
  color: white;
  text-align: center;
  padding: 8px;
  font-weight: bold;
  font-size: 14px;
  border-bottom: 1px solid #ddd;
}

.custom-timetable-cell-content {
  padding: 12px;
  text-align: center;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.custom-timetable-cell-content.free {
  background-color: var(--timetable-free-cell);
  color: var(--dark-primary-color);
}

.custom-timetable-cell-content.busy {
  background-color: #fbe9e7;
  color: #b71c1c;
  cursor: not-allowed;
}

.custom-timetable-cell-content.free:hover {
  background-color: var(--timetable-free-cell-hover);
}

.custom-timetable-cell-content.busy:hover {
  background-color: #f5c6c6;
}

.custom-timetable-cell-content.busy:disabled {
  cursor: not-allowed;
}


.holiday-column {
  background-color: #fff3e0;
}

.custom-timetable-cell-content-title.holiday-title {
  background-color: #ff7043;
  color: white;
  font-weight: bold;
  font-size: 14px;
  padding: 8px;
}

.holiday-name {
  font-size: 12px;
  font-weight: normal;
  margin-top: 4px;
  color: #fff8e1;
  background-color: rgba(255, 255, 255, 0.15);
  border-radius: 6px;
  padding: 2px 6px;
}

.timetable-column {
  display: flex;
  flex-direction: column;
}

.holiday-full-cell {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  border-radius: 16px;
  background: #ffd8b9;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08), inset 0 0 4px rgba(255, 255, 255, 0.4);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: default;
}

.holiday-full-cell:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 25px rgba(0, 0, 0, 0.1), inset 0 0 6px rgba(255, 255, 255, 0.5);
}

.holiday-full-content {
  text-align: center;
}

.holiday-title-large {
  font-size: 1.25rem;
  font-weight: 700;
  color: #4e342e;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.5);
}

.holiday-name-large {
  margin-top: 6px;
  font-size: 1rem;
  font-weight: 500;
  color: #6d4c41;
  opacity: 0.9;
}

</style>