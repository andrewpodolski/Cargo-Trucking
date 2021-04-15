import { shallowMount, createLocalVue } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { Vuelidate } from 'vuelidate'
import Vuex from 'vuex'

import { cars } from './cars.json'
import CarListContent from '../../../src/components/content/car/CarListContent'

const localVue = createLocalVue()
localVue.use(VueRouter)
localVue.use(Vuelidate)
localVue.use(Vuex)
const router = new VueRouter()

const actions = {
  deleteClick: jest.fn()
}

const store = () => {
  return new Vuex.Store({
    actions,
    state: {
      car: {
        items: () => cars
      }
    }
  })
}

describe('CarListContent component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(CarListContent, { localVue, router, store })
  })

  it('renders list correctly', () => {
    expect(wrapper.find('md-table').exists()).toBeTruthy()

  })
})
