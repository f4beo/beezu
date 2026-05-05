# Beezu: Manual de Convenções e Padronização 

## 1. Gestão de Fluxo (GitHub Projects)
Nada deve ser commitado sem uma Issue correspondente.

###  O Quadro Kanban
* **Backlog:** Ideias e requisitos futuros.
* **Ready:** Tarefas priorizadas e prontas para serem puxadas.
* **In Progress:** Tarefas sendo executadas (máximo 1 por desenvolvedor).
* **In Review:** Pull Request (PR) aberto. Aguardando revisão de outro par.
* **Done:** Código mergeado em `develop` e issue fechada.

> **Dica:** Ao abrir um PR, colocar o comando `Closes #ID_DA_ISSUE` na descrição para que o GitHub feche a tarefa automaticamente após o merge.

---

## 2. Estratégia de Git (Gitflow Simplificado)
Trabalharemos com três níveis de branches:

1.  **`main`**: Apenas código estável e testado.
2.  **`develop`**: Branch de integração. Todo desenvolvimento converge para cá.
3.  **`tipo/ID-descricao`**: Branches temporárias para tarefas específicas.
    * *Exemplo:* `feature/12-CRUD-tarefa` que vai estar ligada com a issue `#12: Crud de tarefas (frontend)`
    * *Exemplo:* `fix/46-bug-dashboard` que vai estar ligada com a issue `#46: Bug na rota /dashboard/metrics (api)`

**Fluxo de Trabalho:**
* Antes de desenvolver, confira se está na branch `develop` e de um `git pull` na develop antes de criar sua branch.
* Nunca faça commits diretos em `main` ou `develop`.
* **Revisão:** Todo PR para `develop` exige ao menos **1 aprovação** de outro colega.

---

## 3. Padrão de Commits (Conventional Commits)
Para manter o histórico legível e permitir automações, as mensagens de commit devem seguir o formato:
`tipo(escopo): descrição curta em letra minúscula`

### Tipos Permitidos
| Tipo | Descrição |
| :--- | :--- |
| **feat** | Nova funcionalidade ou recurso. |
| **fix** | Correção de um erro/bug. |
| **docs** | Alterações apenas em documentação. |
| **style** | Mudanças de formatação/estética que não afetam a lógica. |
| **refactor** | Mudança no código que não corrige bug nem adiciona funcionalidade. |
| **chore** | Atualização de dependências, configurações de build e ferramentas. |
| **test** | Adição ou modificação de testes. |

**Exemplos:**
* `feat(web): implementar dashboard da colmeia`
* `fix(api): corrigir cálculo de evolução da abelha`
* `docs(repo): atualizar instruções de instalação no README`

> **Detalhe:** tem um script na pasta raiz do projeto que não permite dar commit sem estar com a mensagem dentro do padrão.

---

## 4. Estrutura do Repositório
O repositório principal abrigará o ecossistema Web e Backend para facilitar a gestão de contratos e tipos.

```text
/beezu
├── .husky/             # Hooks de automação do Git
├── web/                # Pasta do frontend 
├── api/                # Pasta do backend
├── docs/               # Documentação técnica e diagramas
├── commitlint.config.js
└── package.json        # Configurações globais do projto
```
*O projeto **Mobile (Flutter)** será mantido em um repositório separado para evitar conflitos.*

---

## 5. Qualidade e Blindagem de Código
Para garantir que as regras acima sejam cumpridas, utilizaremos:

* **Commitlint:** Bloqueia commits que não seguem o padrão.
* **Husky:** Dispara o Commitlint automaticamente antes de cada `git commit`. ~~Além disso roda os testes automatizados pré configurados antes do commit~~.
* ~~**ESLint/Prettier (Web):** Padronização visual do código TypeScript.~~
* ~~**Checkstyle (Java):** Garantia de padrões da comunidade Java no Spring Boot.~~

---

## 6. Comunicação e Decisões
* **Dúvidas Técnicas:** Devem ser discutidas nas Issues ou no canal de dev.
* **Mudança de Requisito:** Se algo no diagrama precisar mudar durante o código, a alteração deve ser documentada no repositório de documentação antes da implementação final.

---

Este guia é iterativo. Se o time sentir que algo está travando o processo, propomos uma mudança em grupo. 
