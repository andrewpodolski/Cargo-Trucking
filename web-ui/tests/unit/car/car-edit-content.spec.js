import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'
import http from 'vue-resource'

import CarEditContent from '../../../src/components/content/car/CarEditContent'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(http)
const router = new VueRouter()

describe('CarEditContent component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(CarEditContent, {
      localVue,
      router
    })
  })

  it('renders correctly', () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field').length).toBe(4)
  })

  it('renders error messages when empty form is submitted', async () => {
    await wrapper.setData({
      number: '',
      fuelConsumption: '',
      loadCapacity: '',
      carType: ''
    })

    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.findAll('.md-error').length).toBe(3)
    expect(wrapper.find('.md-error').text()).toBe('Field is required')
  })
})
