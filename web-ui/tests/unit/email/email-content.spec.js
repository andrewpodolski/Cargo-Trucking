import { shallowMount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { Vuelidate } from 'vuelidate'
import http from 'vue-resource'

import EmailContent from '../../../src/components/content/email/EmailContent'

const localVue = createLocalVue()
const router = new VueRouter()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(http)

describe('EmailContent', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(EmailContent, {
      localVue,
      router
    })
  })

  afterEach(() => {
    wrapper && wrapper.destroy()
  })

  it('renders correctly', () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field')).toHaveLength(3)
  })

  it('renders server error message', async () => {
    await wrapper.setData({
      hasError: true,
      errorMessage: '404 NOT FOUND'
    })

    expect(wrapper.find('md-chip').text()).toBe('404 NOT FOUND')
  })
})
