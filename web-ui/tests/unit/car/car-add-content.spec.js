import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'

import CarAddContent from '../../../src/components/content/car/CarAddContent'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
const router = new VueRouter()

describe('CarAddContent component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(CarAddContent, {
      localVue,
      router
    })
  })

  it('renders correctly', async () => {
    expect(wrapper.find('md-input[name="number"]')).toBeTruthy
    expect(wrapper.find('md-input[name="fuelConsumption"]')).toBeTruthy
    expect(wrapper.find('md-input[name="loadCapacity"]')).toBeTruthy
    expect(wrapper.find('md-select')).toBeTruthy
    expect(wrapper.find('md-card-actions').exists()).toBeTruthy
  })

  it('renders error messages when the form is empty', async () => {
    await wrapper.setData({
      number: 0
    })
    await wrapper.vm.$nextTick()

    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()
    expect(wrapper.findAll('.md-error').length).toBe(4)
    expect(wrapper.find('.md-error').text()).toBe('Field must be maximum 10 characters')
  })
})
