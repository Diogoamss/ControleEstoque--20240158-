#!/usr/bin/env pwsh

Write-Host "Iniciando servidor Spring Boot..." -ForegroundColor Cyan

$serverProcess = Start-Process `
  -FilePath "java" `
  -ArgumentList @("-jar", "api-estoque-0.0.1-SNAPSHOT.jar") `
  -WorkingDirectory "$PSScriptRoot\target" `
  -WindowStyle Normal `
  -PassThru

Write-Host "Aguardando inicialização (10s)..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

Write-Host "`n=== TESTE 1: GET /api/categorias ===" -ForegroundColor Cyan
try {
  $categories = Invoke-RestMethod -Uri "http://localhost:8080/api/categorias" -Method Get
  Write-Host "✓ Sucesso! Categorias encontradas: $($categories.Count)" -ForegroundColor Green
  $categories | Select-Object -First 1 | Format-Table
} catch {
  Write-Host "✗ Erro: $_" -ForegroundColor Red
}

Write-Host "`n=== TESTE 2: POST /api/produtos ===" -ForegroundColor Cyan
$produtoJson = @{
  nome = "Notebook Dell XPS 13"
  preco = 4500
  categoriaId = 1
  fornecedorIds = @(1)
  estoqueQuantidade = 5
} | ConvertTo-Json

$headers = @{"Content-Type" = "application/json"}

try {
  $response = Invoke-WebRequest `
    -Uri "http://localhost:8080/api/produtos" `
    -Method Post `
    -Headers $headers `
    -Body $produtoJson

  Write-Host "✓ Código HTTP: $($response.StatusCode)" -ForegroundColor Green
  $produto = $response.Content | ConvertFrom-Json
  Write-Host "✓ Produto criado:"
  Write-Host "  ID: $($produto.id)"
  Write-Host "  Nome: $($produto.nome)"
  Write-Host "  Preço: $($produto.preco)"
} catch {
  Write-Host "✗ Erro ao criar produto: $_" -ForegroundColor Red
  Write-Host "Response: $($_.Exception.Response)" -ForegroundColor Yellow
}

Write-Host "`n=== TESTE 3: POST /api/vendas ===" -ForegroundColor Cyan
$vendaJson = @{
  clienteId = 1
  itens = @(
    @{ produtoId = 1; quantidade = 2 }
  )
} | ConvertTo-Json -Depth 3

try {
  $response = Invoke-WebRequest `
    -Uri "http://localhost:8080/api/vendas" `
    -Method Post `
    -Headers $headers `
    -Body $vendaJson

  Write-Host "✓ Código HTTP: $($response.StatusCode)" -ForegroundColor Green
  $venda = $response.Content | ConvertFrom-Json
  Write-Host "✓ Venda criada:"
  Write-Host "  ID: $($venda.id)"
  Write-Host "  Total: $($venda.total)"
} catch {
  Write-Host "✗ Erro ao criar venda: $_" -ForegroundColor Red
}

Write-Host "`nEncerrando servidor..." -ForegroundColor Cyan
Stop-Process -Id $serverProcess.Id -Force
Write-Host "✓ Concluído!" -ForegroundColor Green
