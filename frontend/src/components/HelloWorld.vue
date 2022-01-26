<template>
  <div>
    <h1>안녕, 세상아</h1>
    <p>CI/CD 성공!!!!!!!!!!</p>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<style scoped>
h1 {
  margin: 40px 0 0;
}
p {
  color: #42b983;
}
</style>
