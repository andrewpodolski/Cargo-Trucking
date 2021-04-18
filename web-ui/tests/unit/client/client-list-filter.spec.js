import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'
import Vuex from 'vuex'

import ClientListFilter from '../../../src/components/content/client/ClientListFilter'
import { clients } from './clients.json'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(Vuex)

const router = new VueRouter()
const state = {
  items: clients,
  totalElements: clients.length,
  filter: {
    name: 'name',
    status: []
  },
  sort: 'ASC'
}

const store = new Vuex.Store({
  modules: {
    client: {
      state
    }
  }
})


describe('ClientListFilter component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(ClientListFilter, {
      localVue,
      router,
      store
    })
  })

  it('renders correctly', () => {
    expect(wrapper.find('.clientSearch').exists).toBeTruthy()
    expect(wrapper.findAll('md-checkbox').length).toBe(2)
    expect(wrapper.find('md-card-actions').exists()).toBeTruthy()
  })
})
