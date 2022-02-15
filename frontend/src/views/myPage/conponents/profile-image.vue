<template>
  <div class="profile-img-area">
    <img class="profile-img" :src="state.img" @click="state.profileImageDialogVisible = true">
    <img class="tier-img" :src="state.tier[state.user.tier]">
  </div>
  <el-dialog
    v-model="state.profileImageDialogVisible"
    title="Tips"
    width="30%"
  >
    <span>This is a message</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="state.profileImageDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="state.profileImageDialogVisible = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-image',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      img: require('../../../assets/images/ssafy.png'),
      tier: {
        'Iron': require('../../../assets/images/tier/iron.png'),
        'Bronze': require('../../../assets/images/tier/bronze.png'),
        'Silver': require('../../../assets/images/tier/silver.png'),
        'Gold': require('../../../assets/images/tier/gold.png'),
        'Diamond': require('../../../assets/images/tier/diamond.png'),
      },
      profileImageDialogVisible : false,

    })

    return {
      state
    }
  }
}
</script>

<style scoped>
  .profile-img-area {
    background : #3ac2574f;
    height: 300px;
    line-height: 300px;
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

  /* .profile-img-area strong {
    position: absolute;
    top:200px;
    left: 50%;
    margin-left: -40px;
  } */
</style>
