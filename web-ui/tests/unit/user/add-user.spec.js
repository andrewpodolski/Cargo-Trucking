import { shallowMount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { Vuelidate } from 'vuelidate'
import http from 'vue-resource'

import AddUser from '../../../src/components/content/user/AddUser'

const localVue = createLocalVue()
const router = new VueRouter()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(http)

describe('AddUser', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(AddUser, {
      localVue,
      router
    })
  })

  it('renders correctly', async () => {
    expect(wrapper.find('form').exists()).toBeTruthy()
    expect(wrapper.findAll('md-field')).toHaveLength(12)
    expect(wrapper.find('md-datepicker')).toBeTruthy()
  })

  it('renders with validation errors', async () => {
    await wrapper.setData({
      name: null,
      surname: null,
      patronymic: null,
      email: null,
      userRoles: null,
      bornDate: null,
      login: null,
      password: null,
      passwordConfirm: null,
      town: null,
      street: null,
      house: null,
      flat: null
    })

    await wrapper.vm.$nextTick()
    await wrapper.vm.$emit('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.findAll('.md-error')).toHaveLength(7)
    expect(wrapper.find('.md-error').text()).toBe('Field is required')
  })
})
