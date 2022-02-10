<template>
  <div v-if="true" style="background-color:#d0d0d0; margin:5rem; border:5px solid; padding:7rem;">
      <h2 style="text-align:center">로그인 하시면 나의 공부 정보를 볼 수 있어요!</h2>
  </div>
  <div style="display:flex; justify-content:space-around; align-items:center;">
    <h3>Study List</h3>
    <h3/><h3/><h3/>
  </div>
  <hr width = "80%"/>
  <div style="display:flex; justify-content:space-around; align-items:center;">
    <form action="#" method="post">
      <div>
      <el-select v-model="day" class="m-2" placeholder="한달">
        <el-option
          v-for="day in days"
          :key="day.value"
          :label="day.label"
          :value="day.value"
        >
        </el-option>
      </el-select>
        <el-select v-model="group" class="m-2" placeholder="전체">
        <el-option
          v-for="group in groups"
          :key="group.value"
          :label="group.label"
          :value="group.value"
        >
        </el-option>
      </el-select>
      </div>
      <div>
      <el-input
          v-model="search"
          class="m-2"
          placeholder="아이디 입력"
          :suffix-icon="Search"
      ></el-input>
      </div>
       <el-button plain type="success">검색</el-button>

    </form>


  </div>
<div style="display:flex; justify-content:space-around; align-items:center;">

  <el-table :data="tableData"  height="250" stripe style="width: 100%" align="center">
    <el-table-column prop="rank" label="랭크" width="180" />
    <el-table-column prop="nickname" label="닉네임" width="180" />
    <el-table-column prop="tear" label="티어" />
    <el-table-column prop="group" label="소속" />
    <el-table-column prop="time" label="공부한 시간" />
  </el-table>

</div>


<div align="center">
  <!--순위 많을때 번호...?-->
</div>
</template>

<script>

// export default {
//   name: 'ranking',
// }

// import { ref } from 'vue'
//왜 안되지...
//import { Search } from '@element-plus/icons-vue'
// const day = ref('')
// const group = ref('')
// const search = ref('')
// const days = [
//   {
//     value: '1',
//     label: '1일',
//   },
//   {
//     value: '2',
//     label: '7일',
//   },
//   {
//     value: '3',
//     label: '한달',
//   },
// ]

// const groups = [
//   {
//     value: '1',
//     label: '전체',
//   },
//   {
//     value: '2',
//     label: '내 소속',
//   },
//   {
//     value: '3',
//     label: '친구',
//   },
// ]


// const tableData = [
//   {
//     date: '2016-05-04',
//     name: 'Tom',
//     address: 'No. 189, Grove St, Los Angeles',
//   },
//   {
//     date: '2016-05-01',
//     name: 'Tom',
//     address: 'No. 189, Grove St, Los Angeles',
//   },
//   {
//     date: '2016-05-08',
//     name: 'Tom',
//     address: 'No. 189, Grove St, Los Angeles',
//   },
//   {
//     date: '2016-05-06',
//     name: 'Tom',
//     address: 'No. 189, Grove St, Los Angeles',
//   },
//   {
//     date: '2016-05-07',
//     name: 'Tom',
//     address: 'No. 189, Grove St, Los Angeles',
//   },
// ]
</script>

<style>

</style>
