# 💣 Campo Minado (Mobile)

## 🧾 Descrição

Trata-se de uma implementação completa do clássico jogo "Campo Minado" para Android. O objetivo principal do projeto é aplicar os conceitos de arquitetura limpa e desenvolvimento moderno, incluindo: 
- **Arquitetura MVVM**
- **Persistência local com Room Database**
- **Integração com fonte de dados externa (Firebase)**
- **Interface reativa com Jetpack Compose**

---

## 🚀 Como Funciona?

O aplicativo é dividido em dois perfis de acesso distintos (Jogador e Administrador), para demonstrar a separação de lógica e controle de acesso.

#### 👤 Perfil de Jogador (Usuário Comum)

O jogador pode: 
- **Jogar:** Iniciar um novo jogo de Campo Minado.
- **Escolher dificuldade:** Selecionar um modo de jogo (Ex: "Fácil", "Médio") que é carregado dinamicamente do Firebase.
- **Ver ranking:** Acessar a tela de recordes, que exibe os melhores tempos salvos localmente no banco de dados Room. Esta lista é exibida em uma LazyColumn 8e é atualizada reativamente usando Flow.
- **Configurações:** Ajustar preferências (como som ou vibração), que são salvas na segunda entidade local do Room.

#### 🔑 Perfil de Administrador (Gerenciamento)

O Administrador pode:
- **Gerenciar dificuldades:** Acessar um "dashboard" que se conecta ao Firebase.
- **CRUD completo:** O Admin pode Criar, Ler, Atualizar e Deletar os modos de dificuldade (Ex: adicionar um modo "Expert" ou ajustar o número de minas do modo "Fácil").
- **Sincronia:** Qualquer alteração feita pelo Admin no Firebase é refletida automaticamente para todos os jogadores que abrirem o app.

---

## 📱 Telas e Vídeo de Demonstração
lalala

---

## 👥 Autores
#### Responsável por A, B e C:  [CrystoferAT](https://github.com/CrystoferAT)
#### Responsável por X, Y e Z:  [josssgz](https://github.com/josssgz)
