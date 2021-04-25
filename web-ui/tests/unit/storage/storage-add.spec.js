import { shallowMount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { Vuelidate } from 'vuelidate'

import StorageAddContent from '../../../src/components/content/storage/StorageAddContent'

const localVue = createLocalVue()
const router = new VueRouter()
localVue.use(VueRouter)
localVue.use(Vuelidate)

describe('StorageAddContent', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(StorageAddContent, {
      localVue,
      router
    })
  })

  it('renders correctly', async () => {
    await wrapper.setData({
      service: {}
    })

    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-input')).toHaveLength(1)
    expect(wrapper.findAll('md-autocomplete')).toHaveLength(1)
  })

  it('renders with validation errors', async () => {
    await wrapper.setData({
      name: null,
      address: null
    })
    await wrapper.vm.$nextTick()
    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.findAll('.md-error')).toHaveLength(2)
    expect(wrapper.find('.md-error').text()).toBe('Field is required')
  })

  it('renders with server errors', async () => {
    await wrapper.setData({
      errorMessage: '404 Not Found',
      hasError: true
    })

    await wrapper.vm.$nextTick()
    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('md-chip').exists()).toBeTruthy()
    expect(wrapper.find('md-chip').text()).toBe('404 Not Found')
  })
})
