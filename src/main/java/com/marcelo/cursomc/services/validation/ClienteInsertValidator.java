package com.marcelo.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.marcelo.cursomc.domain.enums.TipoCliente;
import com.marcelo.cursomc.dto.ClienteNewDTO;
import com.marcelo.cursomc.resources.exceptions.FieldMessage;
import com.marcelo.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>
{
	@Override
	public void initialize(ClienteInsert ann)
	{
	}

	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context)
	{
		List<FieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista
		if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj()))
		{
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if(clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj()))
		{
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		
		for (FieldMessage e : list)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
