module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    // Regra para quando fizer algum commit sempre indicar o escopo (por ser monorepo)
    'scope-enum': [2, 'always', [
      'web', 
      'mobile', 
      'api', 
      'docs', 
      'ci', 
      'repo'
    ]],
    'type-enum': [2, 'always', [
      'feat',     // Nova funcionalidade
      'fix',      // Correção de bug
      'docs',     // Documentação
      'style',    // Formatação, semântica (CSS, etc)
      'refactor', // Refatoração de código
      'test',     // Testes
      'chore'     // Tarefas de build, pacotes, etc
    ]],
  },
};
