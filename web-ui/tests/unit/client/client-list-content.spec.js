import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'
import Vuex from 'vuex'

import ClientList from '../../../src/components/content/client/ClientList'
import { clients } from './clients.json'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(Vuex)

const router = new VueRouter()
const state = {
  items: clients,
  totalElements: clients.length
}

const store = new Vuex.Store({
  modules: {
    client: {
      state
    }
  }
})

describe('ClientList component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(ClientList, {
      localVue,
      router,
      store
    })
  })

  it('renders correctly', async () => {
    expect(wrapper.find('md-table').exists()).toBeTruthy()
  })
})
