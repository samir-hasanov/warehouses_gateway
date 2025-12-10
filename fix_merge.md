# Git Merge Problemi Həll Addımları

## Problem:
- Branch3-ü dev-ə merge edilib, amma dev-də kodlar görünmür
- Branch01 dev-dən yaradılıb, amma branch3-dəki exception klasları yoxdur

## Həll Addımları:

### 1. Dev branch-ə keçin:
```bash
git checkout dev
```

### 2. Dev branch-ə branch3-ü yenidən merge edin:
```bash
git merge branch3
```

### 3. Əgər conflict varsa, həll edin və commit edin:
```bash
git add .
git commit -m "Merge branch3 into dev - fix missing files"
```

### 4. Branch01-i yeniləyin (dev-dən):
```bash
git checkout branch01
git merge dev
```

### 5. Remote-a push edin:
```bash
git push origin dev
git push origin branch01
```

## Alternativ Həll (Branch3-dəki faylları manual kopyalama):

1. Branch3-ə keçin:
```bash
git checkout branch3
```

2. Exception klaslarını yoxlayın:
```bash
ls src/main/java/www/stock/az/exception/
```

3. Dev-ə keçin və branch3-dəki faylları cherry-pick edin:
```bash
git checkout dev
git cherry-pick <branch3-commit-hash>
```

