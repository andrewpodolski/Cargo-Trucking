module.exports = {
  'root': true,
  'env': {
    'node': true,
    'es6': true
  },
  'extends': [
    'plugin:vue/essential',
    'plugin:import/warnings',
    'plugin:import/errors',
    'eslint:recommended'
  ],
  'rules': {
    'quotes': [
      'error',
      'single',
      {
        'avoidEscape': true
      }
    ],
    'comma-style': [
      2,
      'last'
    ],
    'comma-dangle': [
      2,
      'never'
    ],
    'semi': [
      2,
      'never'
    ],
    'import/order': [2, {
      'groups': [
        'external',
        ['sibling', 'parent'],
        'index'
      ],
      'newlines-between': 'always'
    }],
    'import/no-unresolved': 'off'
  },
  'overrides': [
    {
      files: ['*.vue'],
      rules: {
        'import/order': [
          'error',
          {
            groups: [
              [
                'builtin',
                'external'
              ],
              [
                'index',
                'internal',
                'parent',
                'sibling'
              ]
            ],
            'newlines-between': 'always',
            alphabetize: {
              order: 'asc',
              caseInsensitive: true
            }
          }
        ]
      }
    }
  ],
  'parserOptions': {
    'parser': 'babel-eslint'
  }
}
