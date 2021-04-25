import { shallowMount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import http from 'vue-resource'

import MessagesList from '../../../src/components/content/driversChat/MessagesList'

const router = new VueRouter()
const localVue = createLocalVue()
localVue.use(Vuex)
localVue.use(VueRouter)
localVue.use(http)

describe('MessagesList', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(MessagesList, {
      localVue,
      router
    })

  })

  it('renders correctly', () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
  })
})
