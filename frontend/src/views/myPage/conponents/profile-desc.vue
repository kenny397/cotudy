<template>
  <div>
    <div class="profile-nickname-area">{{state.user.nickName}}</div>
    <div class="tier-container">
      <img class="tier-image" :src="state.tier[state.user.tier]" alt="">
      <span>{{state.user.tier}}</span>
    </div>
    <div class="desc-container">
      <div style="text-align:left;">
        <p class="margin-left-5px">
          <font-awesome-icon icon="clock" />
          <strong class="margin-left-5px"> {{state.user.entireStudyTime}} </strong>
          공부
        </p>
        <p>
          <font-awesome-icon icon="user-group" />
          <strong class="margin-left-5px">{{state.user.rivalCount}}</strong>
          명의 친구
        </p>
      </div>
      <div>
        <p>
          <font-awesome-icon icon="school" />
          <span class="font-color-light margin-left-5px">{{state.department[state.user.departmentId]}}</span>
        </p>
      </div>
      <div style="margin-top: 30px;">
        <span
          style="background: #3AC258; padding:10px; border-radius: 15px; color: white; font-size:20px;"
          v-if="state.user.isRival && !state.user.isMe"
          @click="editRival(true)"
        >
          <font-awesome-icon icon="user-plus" />
        </span>
        <span
          style="background: red; padding:10px; border-radius: 15px; color: white; font-size:20px;"
          v-if="!state.user.isRival && !state.user.isMe"
          @click="editRival(false)"
        >
          <font-awesome-icon icon="user-minus" />
        </span>
      </div>
    </div>
  </div>
</template>

<style >
  .desc-container {
    display: flex;
    justify-content : space-between;
    flex-direction: row;
  }
  .desc-container div {
    width: 200px;
  }
  .margin-left-5px {
    margin-left: 5px;
  }
  .font-color-light {
    color: #8A8F95;
  }
  .tier-image {
    width: 50px;
  }
  .tier-container {
    height:50px;
    line-height: 50px;
  }
  .tier-container *{
    vertical-align: middle;
  }
  .profile-nickname-area {
    font-size: 25px;
    font-weight: 900;
    margin-bottom: 10px;
  }
</style>

<script>
import { reactive} from 'vue';
import axios from 'axios';

export default {
  name: 'profile-desc',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      isMe : props.isMe,
      isRival : props.isRival,
      department : {
        1: 'SSAFY',
        2: '삼성전자',
        3: '삼성멀티캠퍼스',
        4: '대전고등학교',
        5: '서울고등학교',
        10: '소속없음',
      },
      tier: {
        'Iron': require('../../../assets/images/tier/iron.png'),
        'Bronze': require('../../../assets/images/tier/bronze.png'),
        'Silver': require('../../../assets/images/tier/silver.png'),
        'Gold': require('../../../assets/images/tier/gold.png'),
        'Diamond': require('../../../assets/images/tier/diamond.png'),
      },



    })
    const editRival = function(isAdd) {
      if (isAdd) {
        // 라이벌 추가
        axios({
          url: 'users/rival',
            method: 'post',
            data: {
              'rivalId': state.user.id,
              'userId': localStorage.getItem('userId')*1
            }
          })
          .catch(err => {
            console.log(err)
          })

      } else {
        // 라이벌 삭제
        axios({
          url: 'users/rival',
            method: 'delete',
            data: {
              'rivalId': state.user.id,
              'userId': localStorage.getItem('userId')*1
            }
          })
          .catch(err => {
            console.log(err)
          })
      }
    }




    return {
      state,
      editRival
    }
  }

}
</script>


