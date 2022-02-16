<template>
  <div class="profile-img-area">
    <img class="profile-img" :src="state.profileImg[state.user.thumbnail]" @click="state.profileImageDialogVisible = true">
    <img class="tier-img" :src="state.tier[state.user.tier]">
  </div>
  <el-dialog
    v-model="state.profileImageDialogVisible"
    title="프로필 이미지 변경"
    width="40%"
    custom-class="profile-image-dialog"
  >
    <span>프로필 이미지를 선택해주세요.</span>
    <div class="profile-img-box" >
      <img
        :src="state.profileImg[imgKey]"
        v-for="imgKey in Object.keys(state.profileImg)"
        :key="imgKey"
        @click="profileImageEdit(imgKey)"
        style="cursor : pointer;"
      >
    </div>
  </el-dialog>
</template>

<style scoped>
  .profile-img-area {
    background : #3ac2574f;
    height: 300px;
    line-height: 300px;
  }
  .profile-img-box{
    margin-top:10px; border:1px solid rgba(0, 0, 0, 0.253); border-radius: 20px;
  }
  .profile-img-box img {
    cursor: pointer;
  }

  .profile-img-box img:hover {
    border: 3px solid #3AC258;

  }

  .profile-img-area .profile-img {
    vertical-align: middle;
    width:160px;
    height:160px;
    border-radius: 160px;
    border: 1px solid rgba(0, 0, 0, 0.253);
    cursor: pointer;
  }

  .profile-img-area .tier-img {
    position: absolute;
    width: 140px;
    left: 50%;

    margin-left: -62px;
  }
  .el-dialog img {
    width: 200px;
    height:200px;
    border-radius:200px;
    margin: 10px;
    border: 3px solid rgba(0, 0, 0, 0.253);
  }

  /* .profile-img-area strong {
    position: absolute;
    top:200px;
    left: 50%;
    margin-left: -40px;
  } */
</style>
<style>
  .profile-image-dialog {
    border-radius: 20px;
    background: mintcream;

  }
</style>

<script>
import { reactive,} from 'vue';
import axios from 'axios';

export default {
  name: 'profile-image',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      tier: {
        'Iron': require('../../../assets/images/tier/iron.png'),
        'Bronze': require('../../../assets/images/tier/bronze.png'),
        'Silver': require('../../../assets/images/tier/silver.png'),
        'Gold': require('../../../assets/images/tier/gold.png'),
        'Diamond': require('../../../assets/images/tier/diamond.png'),
      },
      profileImageDialogVisible : false,
      profileImg: {
        '1' : require('../../../assets/images/profile/profile1.png'),
        '2' : require('../../../assets/images/profile/profile2.png'),
        '3' : require('../../../assets/images/profile/profile3.png'),
        '4' : require('../../../assets/images/profile/profile4.png'),
        '5' : require('../../../assets/images/profile/profile5.png'),
        '6' : require('../../../assets/images/profile/profile6.png'),
        '7' : require('../../../assets/images/profile/profile7.png'),
        '8' : require('../../../assets/images/profile/profile8.png'),
      }
    })

    const profileImageEdit= function(imgKey){
      state.profileImageDialogVisible = false
      state.user.thumbnail = imgKey
      axios({
          url: `users/${state.user.id}`,
          method: 'put',
          data: {
            'departmentId': state.user.departmentId,
            'goal': state.user.goal,
            'goalTime': state.user.goalTime,
            'id': state.user.id,
            'nickName': state.user.nickName,
            'thumbnail': state.user.thumbnail,
            'userId': state.user.userId
          }
        })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
    }

    return {
      state,
      profileImageEdit
    }
  }
}
</script>

