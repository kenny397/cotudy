<template>

  <div class="profile-img-area">
    <img class="profile-img" :src="state.img" alt="" style="">
    <img class="tier-img" :src="state.tier" alt="">
    <strong>Diamond VI</strong>
  </div>


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
      tier: require('../../../assets/images/tier/diamond.png')
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
  }

  .profile-img-area .tier-img {
    position: absolute;
    width: 140px;
    left: 50%;
    margin-left: -70px;

  }
  .profile-img-area strong {
    position: absolute;
    top:200px;
    left: 50%;
    margin-left: -40px;
  }
</style>
