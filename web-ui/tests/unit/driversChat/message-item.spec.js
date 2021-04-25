import { shallowMount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import VueRouter from 'vue-router'

import MessageItem from '../../../src/components/content/driversChat/MessageItem'
import models from './mock'

const router = new VueRouter()
const localVue = createLocalVue()
localVue.use(Vuex)
localVue.use(VueRouter)

describe('MessageItem component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = shallowMount(MessageItem, {
      localVue,
      router,
      computed: {
        userName: () => 'andrew_podo1'
      }
    })
  })

  it('renders correctly', async () => {
    await wrapper.setProps({ ...models.defaultModel })

    expect(wrapper.find('md-ripple').exists()).toBeTruthy()
    expect(wrapper.find('span[data-test-id="message"]').text()).toBe(models.defaultModel.messageInfo.message)
    expect(wrapper.findAll('md-icon')).toHaveLength(2)
    expect(wrapper.findAll('md-card-header .md-subhead')).toHaveLength(2)
    expect(wrapper.find('span[data-test-id="is-edited-label"]').exists()).toBeFalsy()
  })

  it('renders edited message', async () => {
    await wrapper.setProps({ ...models.editedMessage })

    expect(wrapper.find('span[data-test-id="is-edited-label"]').exists()).toBeTruthy()
  })

  it('renders message in edit mode', async () => {
    await wrapper.setProps({ ...models.messageInEdit })

    expect(wrapper.find('md-icon[title="close edit"]').exists()).toBeTruthy()
  })
})
