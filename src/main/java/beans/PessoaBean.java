package beans;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import dao.GenericDao;
import entity.Cidades;
import entity.Estados;
import entity.Pessoa;
import jpautil.JPAUTIL;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;
import repository.IDAOPessoa;
import repository.IDAOPessoaImp;

@ManagedBean(name = "pessoaBean")
@ViewScoped
//@Named
//RequestScoped
public class PessoaBean implements Serializable{

	private static final long serialVersionUID = 3969675690219696787L;
	
	private Pessoa pessoa = new Pessoa();
	private GenericDao<Pessoa> genericDao = new GenericDao<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDAOPessoa idaoPessoa = new IDAOPessoaImp();
	private List<SelectItem> estados;
	private List<SelectItem> cidades = new ArrayList<>();
	
	
	private Part arquivoFoto;
	public Part getArquivoFoto() {
		return arquivoFoto;
	}
	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}
	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getEstados() {
		estados = idaoPessoa.listaEstados();
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public String salvar() {
		genericDao.salvar(pessoa);
		listarPessoas();
		return "";
	}
	
	public String pesquisaCEP(AjaxBehaviorEvent envet) {
		System.out.println("Metodo de pesquisaCEP chamado com CEP:"+pessoa.getCep());
		String linha = "";
		StringBuilder jsonResponse = new StringBuilder();
		try {
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			
			
			while((linha = reader.readLine())!=null) {
				jsonResponse.append(linha);
				
			}
			Pessoa gson = new Gson().fromJson(jsonResponse.toString(), Pessoa.class);
			pessoa.setBairro(gson.getBairro());
			pessoa.setCep(gson.getCep());
			pessoa.setLogradouro(gson.getLogradouro());
			pessoa.setComplemento(gson.getComplemento());
			pessoa.setLocalidade(gson.getLocalidade());
			pessoa.setUf(gson.getUf());
			pessoa.setIbge(gson.getIbge());
			pessoa.setGia(gson.getGia());
			pessoa.setDdd(gson.getDdd());
			pessoa.setSiafi(gson.getSiafi());			
			
		} catch (Exception e) {
			mensagem(e.getMessage());
		}
		return "";
	}	
	private byte[] imageToBase64(InputStream stream) throws IOException {
		int tamanho;
		int size = 1024;
		byte[] buffer = null;
		if(stream instanceof ByteArrayInputStream) {
			size = stream.available();
			buffer = new byte[size];
			tamanho = stream.read(buffer, 0, size);
		}else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			buffer = new byte[size];
			while((tamanho = stream.read(buffer,0,size))!= -1) {
				outputStream.write(buffer,0,size);
			}
		}
		return buffer;
	}
	public String merge() throws IOException {
		
		byte[] imagem = IOUtils.toByteArray(arquivoFoto.getInputStream());
		pessoa.setFotoBase64(imagem);
		BufferedImage bufferedImage =  ImageIO.read(arquivoFoto.getInputStream());
		int type  = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB:bufferedImage.getType();
		int largura = 200;
		int altura = 200;
		//miniatura
		BufferedImage miniatura = new BufferedImage(largura, altura, type);
		Graphics2D graphics2d = miniatura.createGraphics();
		graphics2d.drawImage(bufferedImage, 0, 0, largura, altura, null);
		graphics2d.dispose();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String extensao = arquivoFoto.getContentType().split("\\/")[1];
		ImageIO.write(miniatura, extensao, outputStream);
		String fotoMiniatura = "data:"+arquivoFoto.getContentType()+";base64,"+DatatypeConverter.printBase64Binary(outputStream.toByteArray());
		pessoa.setFotoMiniaturaBase64(fotoMiniatura);
		pessoa.setFotoExtensao(extensao);
		pessoa = genericDao.merge(pessoa);
		listarPessoas();
		mensagem("Atualização realizada com sucesso.");
		return "";
	}
	
	private void mensagem(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
	public String delete() {
		genericDao.delete(pessoa);
		listarPessoas();
		return novo();
	}
	
	@PostConstruct
	public void listarPessoas() {
		pessoas = genericDao.getListEntity2PerPag(Pessoa.class);
	}
	
	public String novo() {
		pessoa =new Pessoa();
		return "";
	}
	
	public String limpar() {
		pessoa =new Pessoa();
		return "";
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public GenericDao<Pessoa> getGenericDao() {
		return genericDao;
	}

	public void setGenericDao(GenericDao<Pessoa> genericDao) {
		this.genericDao = genericDao;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public String login() {
		//System.out.println(pessoa.getLogin()+"-----"+pessoa.getSenha());
		Pessoa user = idaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		if(user!= null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("logedUser", user);
			return "teste.xhtml";
		}else {
			mensagem("Senha ou Login invalidos.");
		}
		return"index.xhtml";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("logedUser");
		HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		httpServletRequest.getSession().invalidate();
		System.out.println("deslogando usuario: "+ pessoa.getLogin());
		return "index.xhtml";
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa loggedUser = (Pessoa) externalContext.getSessionMap().get("logedUser");
		return loggedUser.getPerfil().equalsIgnoreCase(acesso);
	}
	public void carregaCidades(AjaxBehaviorEvent ajaxBehaviorEvent) {
		System.out.println("chamou o carrega cidades." + ajaxBehaviorEvent);
		Estados estado = (Estados)((SelectOneMenu)ajaxBehaviorEvent.getSource()).getValue();
		System.out.println(estado);
		if (estado!= null) {
			pessoa.setEstados(estado);
			List<Cidades> cidades = JPAUTIL.getEntityManager().createQuery("FROM Cidades where estados.id = "+estado.getId()).getResultList();
			List<SelectItem> cidadesTemp = new ArrayList<SelectItem>();
			for(Cidades cidade : cidades) {
				cidadesTemp.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(cidadesTemp);
		}
		
	}
	
	public void carregaCidades(Estados est) {
		Estados estado = est;
		System.out.println(estado);
		if (estado!= null) {
			pessoa.setEstados(estado);
			List<Cidades> cidades = JPAUTIL.getEntityManager().createQuery("FROM Cidades where estados.id = "+estado.getId()).getResultList();
			List<SelectItem> cidadesTemp = new ArrayList<SelectItem>();
			for(Cidades cidade : cidades) {
				cidadesTemp.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(cidadesTemp);
		}
		
	}
	
	public void editar() {
		if(pessoa.getCidade()!= null) {
			Estados estado = pessoa.getCidade().getEstados();
			pessoa.setEstados(estado);
			carregaCidades(estado);
			mensagem("Cadastro carregado com sucesso!!");
		}
	}
	
	public void baixarFoto() throws IOException {
		//gera um map com os parametros enviados na requisição
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = parametros.get("fileDownloadId");
		Pessoa pes = genericDao.cosultar(Pessoa.class, fileDownloadId);
		HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-Disposition", "attachment; filename=download."+pes.getFotoExtensao());
		httpServletResponse.setContentType("application/octet-stream");
		httpServletResponse.setContentLength(pes.getFotoBase64().length);
		httpServletResponse.getOutputStream().write(pes.getFotoBase64());
		httpServletResponse.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}
}
