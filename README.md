# FLExercises

Este é meu repositório pessoal para guardar meus projetos de exercício.

Eu estou optando por construir isso usando o ecossistema da lingaugem Kotlin, por conta disso, faço proveito de algumas ferramentas:

- Kotlin: principal linguagem de programação;
- Gradle: gerenciador de dependências e auxiliar para construção;
- Jetpack Compose/Compose Multiplatform: auxiliar para interfaces visuais (quando aplicável).

# Estrutura

Estou organizando o projeto de forma que cada sub-projeto seja um módulo interno do projeto raiz. Dessa forma, assim que forem sendo acrescentados mais implementações, será possível definir configurações globais, através dos mecanismos Gradle.

Por fim, a ideia é apenas exercitar e manter implementações mínimas das ideias básicas.

# Projetos implementados

No momento os seguintes projetos estão implementados:
- **[random-number-generator](random-number-generator):** gera um número aleatório conforme as opções escolhidas pelo usuário;
- **[password-generator](password-generator):** gera uma senha aleatória de tamanho escolhido que contém caracteres dos tipos selecionados;
- **url-shortner:** gera versões curtas de URLs e as salva em um banco de dados simples. As URLs originais podem ser acessadas através da URL encurtada, caso tenha sido previamente encurtada. 