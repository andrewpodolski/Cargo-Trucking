<template>
  <div>
    <md-card md-with-hover v-bind:class="{'edit-card': isInEdit}">
      <md-ripple>
        <md-card-content v-bind:class="{'edit-content': isInEdit}">
          {{messageInfo.message}}
          <span class="action-buttons" v-if="messageInfo.author === this.$store.state.sidebar.username && !isInEdit">
            <md-button class="md-icon-button md-dense md-primary" @click="editMessageItem">
              <md-icon title="edit message">edit</md-icon>
            </md-button>
            <md-button class="md-icon-button md-dense md-accent" @click="deleteMessageItem">
              <md-icon title="delete message">delete</md-icon>
            </md-button>
          </span>
          <span class="action-buttons" v-if="isInEdit">
            <md-button class="md-icon-button md-dense md-accent" @click="closeEditItem">
              <md-icon title="close edit">close</md-icon>
            </md-button>
          </span>
        </md-card-content>
        <md-card-header v-bind:class="{'md-card-header': !isInEdit, 'md-card-header-edit': isInEdit}">
          <div class="md-subhead" v-bind:class="{'edit-time': isInEdit}">Author: {{messageInfo.author}}</div>
          <div class="md-subhead" v-bind:class="{'edit-date': isInEdit}">Date:
            {{moment(messageInfo.date).format(dateFormat)}}
            <span class="md-subhead" v-if="messageInfo.isEdited">(Edited)</span>
          </div>
        </md-card-header>
      </md-ripple>
    </md-card>
  </div>
</template>

<script>
  import moment from 'moment';

  export default {
    name: 'MessageItem',
    props: ['messageInfo', 'editMessage', 'isInEdit', 'closeEdit', 'deleteMessage'],

    data: () => ({
      moment,
      dateFormat: 'MM/DD/YYYY HH:mm'
    }),

    methods: {
      editMessageItem() {
        this.editMessage(this.messageInfo);
      },
      closeEditItem() {
        this.closeEdit();
      },
      deleteMessageItem() {
        this.deleteMessage(this.messageInfo)
      }
    }
  };

</script>

<style scoped>
  .md-card {
    width: 90%;
    margin: 10px auto;
  }
  .md-card-content {
    padding: 15px 15px 0 15px;
    font-size: 16px;
  }
  .md-subhead {
    font-size: 12px;
  }
  .md-card-header {
    padding: 5px 15px 15px 15px;
  }
  .md-card-header-edit {
    padding: 0 15px;
  }
  .action-buttons {
    float: right;
  }
  .edit-card {
    width: 100%;
  }
  .edit-date {
    font-size: 10px;
  }
  .edit-time {
    display: none;
  }
  .edit-content {
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 12px;
    padding: 5px 15px 0px 15px;
  }
</style>
