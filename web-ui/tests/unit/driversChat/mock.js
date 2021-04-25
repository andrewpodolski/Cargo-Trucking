const defaultModel = {
  messageInfo: {
    message: 'Hi! There is a road accident on 68 st. corner. Be careful',
    author: 'andrew_podo1',
    date: new Date(),
    isEdited: false
  },
  isInEdit: false
}

const models = {
  defaultModel: {...defaultModel },
  editedMessage: { ...defaultModel, messageInfo: {isEdited: true}},
  messageInEdit: { ...defaultModel, isInEdit: true}
}

export default models
