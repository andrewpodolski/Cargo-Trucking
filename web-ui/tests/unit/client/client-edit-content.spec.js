import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'
import Vuex from 'vuex'
import http from 'vue-resource'

import ClientEditContent from '../../../src/components/content/client/ClientEditContent'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(Vuex)
localVue.use(http)

const router = new VueRouter()
const errorMessage = 'Service unavailable', hasError = true

describe('ClientEditContent component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(ClientEditContent, {
      localVue,
      router
    })
  })

  it('renders correctly', () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field').length).toBe(2)
  })

  it('renders error message from the api after submit', async () => {
    await wrapper.setData({
      errorMessage,
      hasError
    })
    expect(wrapper.find('md-chip').exists()).toBeTruthy()
    expect(wrapper.find('md-chip').text()).toBe(errorMessage)
  })
})
