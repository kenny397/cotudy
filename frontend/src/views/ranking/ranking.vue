<template>
  <div v-if="true" style="background-color:#d0d0d0; margin:5rem; border:5px solid; padding:7rem;">
      <h2 style="text-align:center">로그인 하시면 나의 공부 정보를 볼 수 있어요!</h2>
  </div>
  <div style="display:flex; justify-content:space-around; align-items:center;">
    <h3>종합 랭킹</h3>
    <h3/><h3/><h3/>
  </div>
  <hr width = "90%"/>
  <div>
    <el-row>
      <el-col :span="2"/>
    <el-col :span="2"> <el-select v-model="term" class="m-2" placeholder="전체">
    <el-option
      v-for="item in terms"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select>
  </el-col>
  <el-col :span="1"/>
    <el-col :span="2">    <el-select v-model="class" class="m-2" placeholder="전체">
    <el-option
      v-for="item in classes"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select></el-col>
<el-col :span="7"/>
    <el-col :span="8">
<div class="mt-4">
    <el-input
      v-model="input"
      placeholder="검색어를 입력하세요"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="아이디" style="width: 100px">
          <el-option label="아이디" value="1"></el-option>
          <el-option label="소속" value="2"></el-option>
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search"></el-button>
      </template>
    </el-input>
  </div>

    </el-col>
  </el-row>

  <el-table :data="rank" stripe border style="width: 100%" :row-class-name="tableRowClassName">
    <el-table-column prop="#" label="" width="50" />
    <el-table-column prop="index+1" label="순위" width="100" />
    <el-table-column prop="nickName" label="닉네임" width="500" />
    <el-table-column prop="department" label="소속" />
    <el-table-column prop="totalStudyTime" label="공부한시간" />
  </el-table>


  </div>

</template>
  
<script>
import { Search } from '@element-plus/icons-vue'
import axios from 'axios';
export default {
  name: 'Ranking',

  components: {

  },

  data(){
    return {
      turm : '',
      terms : [{
        value : 'whole',
        label : '전체'
      },{
        value : 'day',
        label : '1일'
      },{
        value : 'week',
        label : '1주일'
      },{
        value : 'month',
        label : '한달'
      },],
      class : '',
      classes : [{
        value : 'all',
        label : '전체'
      },{
        value : 'department',
        label : '내 소속'
      },{
        value : 'rival',
        label : '친구'
      },],

      select : '',
      conditions : [{
        value : 'id',
        label : '아이디'
      },{
        value : 'department',
        label : '소속'
      },],

      rank : []
    }
  },

  methods: {
    fetchRank(){
      axios.get('ranking').then((Response)=>{
    this.rank = Response.data.content
    }).catch((Error)=>{
    console.log(Error);
})
    }
  },

  created(){
    this.fetchRank();
  },


}
</script>



<style scoped>
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>