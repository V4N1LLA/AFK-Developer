# 🎮 AFK-Developer

**AFK-Developer**는 콘솔 기반 명령어형 방치형 RPG 게임입니다.  
자동 사냥, 장비 수집, 스탯 성장, 보스전 등을 즐기며 캐릭터를 육성할 수 있습니다.  
개발자는 Git 연동 기능을 통해 특정 조건 달성 시 기록을 남기고 특별 이벤트를 실행할 수 있습니다.

---

## 📜 특징

- **자동 사냥**  
  `/hunt start` 로 몬스터와 전투를 시작하면 캐릭터가 자동으로 공격을 진행합니다.
- **보스전**  
  `/boss` 로 강력한 보스를 소환하여 보상을 획득하세요.
- **장비 드랍**  
  몬스터 처치 시 무기, 방어구 등 장비가 드랍됩니다. 희귀도 시스템 적용.
- **스탯 성장**  
  레벨업 시 스탯 포인트와 스킬 포인트를 획득하여 캐릭터를 강화합니다.
- **Git 연동**  
  특정 업적 달성 시 Git 커밋/푸시 등 자동 기록 가능 (향후 기능).

---

## 🗂 프로젝트 구조

```
src/main/java/org/afkdeveloper
├── AfkDeveloperApplication.java
├── Main.java
├── data
│   ├── FileStorage.java
│   ├── GitCommandExecutor.java
│   └── SaveManager.java
├── game
│   ├── command
│   │   ├── Command.java
│   │   ├── CommandHandler.java
│   │   └── commands
│   │       ├── BossCommand.java
│   │       ├── ExitCommand.java
│   │       ├── ForgeCommand.java
│   │       ├── HuntCommand.java
│   │       └── StatusCommand.java
│   ├── logic
│   │   ├── BattleSystem.java
│   │   ├── ForgeSystem.java
│   │   ├── GitSyncService.java
│   │   ├── HuntSystem.java
│   │   ├── LootTable.java
│   │   ├── EquipmentFactory.java
│   │   └── StatManager.java
│   └── model
│       ├── Achievement.java
│       ├── Boss.java
│       ├── Equipment.java
│       ├── Inventory.java
│       ├── Monster.java
│       ├── Player.java
│       ├── Rarity.java
│       └── Stat.java
└── resources
    └── application.properties
```

---

## 🚀 실행 방법

### 1. 빌드
```bash
# PowerShell
.\gradlew.bat clean installDist
```

### 2. 실행
```bash
.uild\install\AFK-Developerin\AFK-Developer.bat
```

### 3. 실행 예시
```plaintext
🎮 AFK-Developer 시작! (/help 로 명령어 보기)
종료: /exit
>> hunt start
Lv.1 Slime HP [■■■■■■□□□□□□□□□□□□] 42.8% (15/35)
🎉 레벨업! Lv.2 (스탯 +2, 스킬포인트 +1)
🎁 드랍 획득: Rusty Dagger +0 (ATK:3 DEF:0)
```

---

## 💻 주요 명령어

| 명령어              | 설명                                      |
|---------------------|-------------------------------------------|
| `/help`             | 사용 가능한 명령어 목록 보기              |
| `/status`           | 캐릭터 상태(레벨, 경험치, 스탯, 장비) 확인 |
| `/hunt start`       | 자동 사냥 시작                            |
| `/hunt stop`        | 사냥 중지                                 |
| `/boss`             | 보스 소환 및 전투                         |
| `/forge`            | 장비 강화                                |
| `/exit`             | 게임 종료                                 |

---

## 📈 성장 시스템

- **스탯**
  - 힘(str): 공격력 증가
  - 명중치(acc): 명중 확률, 치명타 확률 상승
  - 공격속도(aspd): 공격 주기 단축
- **경험치 / 레벨업**
  - 레벨 제한 없음
  - 레벨업 시 스탯 포인트 +2, 스킬 포인트 +1
- **장비**
  - 무기, 방어구, 희귀도(일반, 고급, 희귀, 전설)
  - 드랍 시 능력치가 무작위로 결정됨

---

## 🔮 향후 업데이트 계획

- [ ] 직업 선택 시스템 (전사, 마법사 등)
- [ ] 스킬 시스템 확장 (액티브 / 패시브)
- [ ] 인벤토리 UI 개선
- [ ] Git 연동 업적 기록
- [ ] 던전 및 월드 보스 콘텐츠

---

## 📜 라이선스
MIT License
