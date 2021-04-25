import { shallowMount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { Vuelidate } from 'vuelidate'
import http from 'vue-resource'

import FinanceContent from '../../../src/components/content/sysadmin/FinanceContent'

const localVue = createLocalVue()
const router = new VueRouter()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(http)

describe('FinanceContent', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(FinanceContent, {
      localVue,
      router
    })
  })

  it('renders correctly', () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field')).toHaveLength(3)
  })

  it('renders with validation errors', async () => {
    await wrapper.setData( {
      name: null,
      content: {
        sysAdminPayment: null,
        dbAdminPayment: null,
        serverPayment: null
      }
    })

    await wrapper.vm.$nextTick()
    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.findAll('.md-error')).toHaveLength(3)
    expect(wrapper.find('.md-error').text()).toBe('Field is required')
  })
})
