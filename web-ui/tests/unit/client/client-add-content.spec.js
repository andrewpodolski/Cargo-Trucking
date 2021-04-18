import {shallowMount, createLocalVue} from '@vue/test-utils'
import VueRouter from 'vue-router'
import {Vuelidate} from 'vuelidate'

import ClientAddContent from '../../../src/components/content/client/ClientAddContent'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)

const router = new VueRouter()

describe('ClientAddContent component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(ClientAddContent, {
      localVue,
      router
    })
  })

  it('renders correctly', () => {
    expect(wrapper.find('md-card').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field').length).toBe(9)
  })

  it('renders error messages when empty form is submitted', async () => {
    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()
    expect(wrapper.findAll('.md-error').length).toBe(9)
    expect(wrapper.find('.md-error').text()).toBe('Field is required')
  })

  it('checks clearForm func', async () => {
    await wrapper.setData({
      name: 'FinTechLogistics',
      status: 'PRIVATE',
      cost: '1550'
    })

    await wrapper.vm.$nextTick()
    await wrapper.vm.clearForm()
    await wrapper.vm.$nextTick()

    expect(wrapper.vm.$data.name).toBe(null)
    expect(wrapper.vm.$data.status).toBe('')
    expect(wrapper.vm.$data.cost).toBe(null)
  })
})
