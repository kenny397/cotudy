import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import StudyroomDetail from '@/views/studyroom/studyroom-detail'
import StudyroomStandby from '@/views/studyroom/studyroom-standby'
import StudyroomInside from '@/views/studyroom/studyroom-inside'
import Ranking from '@/views/ranking/ranking'

// eslint-disable-next-line no-undef
const fullMenu = require('@/views/main/menu.json')

function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else { // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push(
    {
      path: '/studyroom/:studyroomId',
      name: 'studyroom-detail',
      component: StudyroomDetail
    },
    {
      path: '/studyroom/standby/:studyroomId',
      name: 'studyroom-standby',
      component: StudyroomStandby
    },
    {
      path: '/studyroom/studyroom-inside/:studyroomId',
      name: 'studyroom-inside',
      component: StudyroomInside
    },
    {
      path: '/ranking/ranking',
      name: 'ranking',
      component: Ranking
    },
    {
      path: '',
      name: 'home',
      component: Home
    }
  )
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
