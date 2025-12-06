-- Add ativo column to all tables for soft delete functionality
ALTER TABLE tb_categorias ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_produtos ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_clientes ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_fornecedores ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_estoques ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_vendas ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
ALTER TABLE tb_itens_venda ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL;
