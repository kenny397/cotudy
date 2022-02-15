<template>
  <el-button
      round
      style="background: #3AC258; float:right; color:white; height:50px; font-size:20px;"
      @click="state.profileEditDialogVisible = true"
    >
      회원정보 수정
  </el-button>
  <el-dialog
    v-model="state.profileEditDialogVisible"
    title="Tips"
    width="30%"
  >
    <span>This is a message</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="state.profileEditDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="state.profileEditDialogVisible = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>

<script>
import { reactive,} from 'vue';
export default {
  name: 'profile-edit-dialog',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      profileEditDialogVisible : false
    })

    return {
      state
    }
  }

}
</script>


