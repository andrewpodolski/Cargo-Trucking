<template>
  <div>
    <form novalidate @submit.prevent="validateContent">
      <md-card>
        <md-card-content>
          <h2>Monthly Costs $</h2>
          <md-field :class="getValidationClass('sysAdminPayment')">
            <label>Admin</label>
            <md-input
              v-model="content.sysAdminPayment"
              name="sysAdminPayment"
              :disabled="sending"/>
            <span class="md-error" v-if="!$v.content.sysAdminPayment.required">
             {{errors.FIELD_IS_REQUIRED}}
           </span>
          </md-field>
          <md-field :class="getValidationClass('dbAdminPayment')">
            <label>Database Admin</label>
            <md-input
              v-model="content.dbAdminPayment"
              name="dbAdminPayment"
              :disabled="sending"/>
            <span class="md-error" v-if="!$v.content.dbAdminPayment.required">
             {{errors.FIELD_IS_REQUIRED}}
           </span>
          </md-field>
          <md-field :class="getValidationClass('serverPayment')">
            <label>Server</label>
            <md-input
              v-model="content.serverPayment"
              name="serverPayment"
              :disabled="sending"/>
            <span class="md-error" v-if="!$v.content.serverPayment.required">
             {{errors.FIELD_IS_REQUIRED}}
           </span>
          </md-field>
        </md-card-content>

        <md-progress-bar md-mode="indeterminate" v-if="sending"/>

        <md-card-actions>
          <md-button
            class="md-primary"
            :disabled="sending"
            to="/clients">
            Back
          </md-button>
          <md-button
            type="submit"
            :disabled="sending"
            class="md-primary">
            Save
          </md-button>

        </md-card-actions>

        <md-chip class="md-accent md-layout md-alignment-center" v-if="hasError">
          {{errorMessage}}
        </md-chip>

      </md-card>
      <md-snackbar :md-active.sync="contentSaved">
        {{messages.INFO_SAVED}}
      </md-snackbar>
    </form>
  </div>

</template>

<script>
  import {between, decimal, required} from 'vuelidate/lib/validators';

  import {Errors} from '../../../constants/errors';
  import {FieldsLength} from '../../../constants/fieldsLength';
  import {FieldsValueBounds} from '../../../constants/fieldsValueBounds';
  import {Messages} from '../../../constants/messages';
  import {Url} from '../../../constants/url';

  export default {
    name: 'FinanceContent',

    data: () => ({
      errors: Errors,
      fieldsLength: FieldsLength,
      messages: Messages,

      name: null,
      content: {
        sysAdminPayment: null,
        dbAdminPayment: null,
        serverPayment: null
      },

      contentSaved: false,
      sending: false,
      errorMessage: null,
      hasError: false
    }),
    validations: {
      content: {
        sysAdminPayment: {
          required,
          decimal,
          between: between(FieldsValueBounds.sysAdminPayment.min, FieldsValueBounds.sysAdminPayment.max)
        },
        dbAdminPayment: {
          required,
          decimal,
          between: between(FieldsValueBounds.dbAdminPayment.min, FieldsValueBounds.dbAdminPayment.max)
        },
        serverPayment: {
          required,
          decimal,
          between: between(FieldsValueBounds.serverPayment.min, FieldsValueBounds.serverPayment.max)
        }
      }
    },
    methods: {
      getValidationClass(fieldName) {
        const field = this.$v[fieldName];
        if (field) {
          return {
            'md-invalid': field.$invalid && field.$dirty
          };
        }
      },
      validateContent() {
        this.$v.$touch();
        if (!this.$v.$invalid) {
          this.saveContent();
        }
      },
      saveContent() {
        this.sending = true;
        const finance = {
          sysAdminPayment: this.content.sysAdminPayment,
          dbAdminPayment: this.content.dbAdminPayment,
          serverPayment: this.content.serverPayment
        };
        this.$http.post(Url.FINANCE, JSON.stringify(finance), {
          headers: {
            Authorization: `Bearer ${localStorage.accessToken}`
          }
        }).then(() => {
          this.contentSaved = true;
          this.sending = false;
          this.content = {};
          this.hasError = false;
          this.$v.$reset();
        }, response => {
          this.hasError = true;
          this.sending = false;
          this.errorMessage = response.body.errors[0];
        });
        this.sending = false;
      }
    },
    mounted: function () {
      const userRoles = JSON.parse(localStorage.getItem('roles'));
      if (!userRoles || !userRoles.includes('SYS_ADMIN')) {
        this.$router.replace('/');
      }

      this.$http.get(Url.LATEST_FINANCE, {
        headers: {
          Authorization: `Bearer ${localStorage.accessToken}`
        }
      })
        .then(res => {
          this.content.sysAdminPayment = res.body.sysAdminPayment;
          this.content.dbAdminPayment = res.body.dbAdminPayment;
          this.content.serverPayment = res.body.serverPayment;
        }, err => {
          this.hasError = true;
          this.errorMessage = err.body.errors[0];
        });
    }
  };

</script>
