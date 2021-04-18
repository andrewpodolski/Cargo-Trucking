<template>
  <div>
    <form novalidate @submit.prevent="validateMessage">
      <md-card>
        <md-card-content>
          <MessageItem
            class="message-in-edit"
            :message-info="messageInEdit"
            v-if="messageInEdit"
            :isInEdit="isInEdit"
            :closeEdit="closeEdit"/>
          <md-field>
            <label class="align-top">Message</label>
            <md-input v-model="text" name="text" :disabled="sending"/>
            <md-button class="md-primary" :disabled="sending" @click="validateMessage">
              <md-icon class="md-icon-button md-dense md-primary">send</md-icon>
            </md-button>
          </md-field>
        </md-card-content>
        <md-chip class="md-accent md-layout md-alignment-center" v-if="hasError">
          {{errorMessage}}
        </md-chip>
      </md-card>
      <md-snackbar :md-active.sync="messageSaved">
        {{textMessages.MESSAGE_SENT}}
      </md-snackbar>
    </form>
    <div v-for="message in messages" v-bind:key="message.id">
      <MessageItem
        :messageInfo="message"
        :editMessage="fillInput"
        :deleteMessage="deleteMessage"></MessageItem>
    </div>
  </div>
</template>

<script>
  import {Errors} from '../../../constants/errors'
  import {FieldsLength} from '../../../constants/fieldsLength'
  import {Messages} from '../../../constants/messages'
  import {Url} from '../../../constants/url'
  import MessageItem from './MessageItem.vue'

  const headers = {
    Authorization: `Bearer ${localStorage.accessToken}`
  }

  export default {
    name: 'MessagesList',

    components: {MessageItem},
    data: () => ({
      errors: Errors,
      fieldsLength: FieldsLength,
      textMessages: Messages,

      sending: false,
      hasError: false,
      errorMessage: null,

      messageSaved: false,
      text: null,
      messages: [],

      messageInEdit: null,
      isInEdit: false
    }),

    methods: {
      getValidationClass(fieldName) {
        const field = this.$v[fieldName]
        if (field) {
          return {
            'md-invalid': field.$invalid && field.$dirty
          }
        }
      },
      validateMessage() {
        this.saveMessage()
      },
      deleteMessage(message) {
        this.sending = true
        this.$http.delete(Url.DRIVERS_CHAT, {
          body: message.id,
          headers
        }).then(() => {
            this.sending = false
            this.hasError = false
          }, res => {
            this.hasError = true
            this.sending = false
            this.errorMessage = res.body.errors[0]
          }).then(() => {
            this.$http.get(`${Url.DRIVERS_CHAT}`, {headers}).then(res => {
              this.messages = res.body.content.reverse()
            })
          })
      },
      saveMessage() {
        this.sending = true

        if (this.isInEdit) {
          this.messageInEdit.message = this.text
          this.$http.patch(Url.DRIVERS_CHAT, JSON.stringify(this.messageInEdit), {headers})
            .then(() => {
              this.messageSaved = true
              this.sending = false
              this.text = null
              this.messageInEdit = null
              this.isInEdit = false
              this.hasError = false
            }, res => {
              this.hasError = true
              this.sending = false
              this.errorMessage = res.body.errors[0]
            })
            .then(() => {
              this.$http.get(`${Url.DRIVERS_CHAT}?page=0&size=1000`, {headers}).then(res => {
                this.messages = res.body.content.reverse()
              })
            })
        } else {
          const message = {
            message: this.text,
            author: this.$store.state.sidebar.username
          }

          this.$http.post(Url.DRIVERS_CHAT, JSON.stringify(message), {headers}).then(() => {
            this.messageSaved = true
            this.sending = false
            this.text = null
            this.hasError = false
          }, res => {
            this.hasError = true
            this.sending = false
            this.errorMessage = res.body.errors[0]
          }).then(() => {
            this.$http.get(`${Url.DRIVERS_CHAT}?page=0&size=1000`, {headers}).then(res => {
              this.messages = res.body.content.reverse()
            })
          })
        }

      },
      fillInput(message) {
        this.isInEdit = true
        this.messageInEdit = message
        this.text = message.message
      },
      closeEdit() {
        this.isInEdit = false
        this.messageInEdit = null
        this.text = null
      }
    },
    mounted: function () {
      const userRoles = JSON.parse(localStorage.getItem('roles'))
      if (!userRoles || !userRoles.includes('DRIVER')) {
        this.$router.replace('/')
      }

      this.$http.get(`${Url.DRIVERS_CHAT}?page=0&size=1000`, {headers}).then(res => {
        this.messages = res.body.content.reverse()
      })
    }
  }
</script>

<style scoped>
  .md-card {
    width: 90%;
    margin: 10px auto;
  }

  .align-top {
    margin-top: 10px;
  }

  .md-input {
    margin-top: 10px
  }

  .message-in-edit {
    width: 100%;
  }
</style>
