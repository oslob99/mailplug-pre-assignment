# MailPlug

### 목차
1. [프로젝트 개요](#mailplug)
2. [기술 버전 정보](#기술-버전-정보)
3. [프로젝트 기능](#프로젝트-기능)
4. [데이터베이스 스키마](#데이터베이스-스키마)
5. [API 명세서](#notion-api-명세서)

### 프로젝트 개요
메일플러그 사전과제입니다.<br> 페이징 처리가 된 메일플러그의 게시판, 게시글, 댓글에 대한 CRUD를 개발하였습니다.

<br><br>
### 📢기술 버전 정보
- 언어 : JAVA(17)
- 데이터베이스 : h2
- 프레임워크 : spring boot(3.1.2)
<br><br>
### 프로젝트 기능
- Board(게시판) CRUD
- Post(게시글) CRUD
- Reply(댓글) CRUD
- 페이징

##### BoardController
```
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oslob/board")
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list(
            PageDTO pageDTO
            , @RequestParam(required = false) String[] types
            ){
        log.info("/api/oslob/board/list?offset={}&limit={}&types={}"
                ,pageDTO.getOffset(),pageDTO.getLimit(),types);
        List<String> typeList = types != null ? Arrays.asList(types) : Collections.emptyList();
        BoardListResponseDTO requestDTO = boardService.getList(pageDTO,typeList);
        return ResponseEntity.ok().body(requestDTO);
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<?> detail(
            @PathVariable Long boardId
    ){
        log.info("/api/oslob/board/detail/{}",boardId);
        try {
            BoardResponseDTO responseDTO = boardService.detail(boardId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다.");
        }
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(
           @Validated @RequestBody BoardRequestWriteDTO writeDTO
           , BindingResult bindingResult
    ){
        log.info("/api/oslob/board/write : writeDTO : {}",writeDTO);
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }
        BoardResponseDTO responseDTO = boardService.write(writeDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @Validated @RequestBody BoardRequestModifyDTO modifyDTO
            , BindingResult bindingResult
    ){
        log.info("/api/oslob/board/modify : modifyDTO : {}",modifyDTO);
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(INVALID_PARAMETER);
        }
        try {
            BoardResponseDTO responseDTO = boardService.modify(modifyDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<?> delete(
            @PathVariable Long boardId
    ){
        log.info("/api/oslob/board/delete/{}",boardId);
        try {
            boardService.delete(boardId);
            return ResponseEntity.ok().body("삭제가 완료되었습니다.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("해당 게시판은 존재하지 않습니다.");
        }
    }
}
```
- 게시판 컨트롤러에서 log로 요청이 제대로 들어오고 있는지 확인하고 있습니다.
- BindingResult를 사용해 유효성 검사를 진행하고 있습니다.
- CRUD로 나누어 주고 비즈니스 로직은 Service에서 처리하도록 Service를 호출하여 줍니다.

#### BoardService
```
@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * @param pageDTO 게시판 페이징 처리 정보
     * @param typeList 게시판 타입을 중복 필터링 할 정보
     * @return 게시판 타입 중복 필터링 처리와 페이징 처리로 조회해 게시판 전체 목록을 반환한다
     */
    public BoardListResponseDTO getList(PageDTO pageDTO, List<String> typeList) {
        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"boardType")
        );
        Page<Board> boards;
        if (typeList.isEmpty()){
            boards = boardRepository.findAll(pageable);
        }else {
            boards = boardRepository.findByBoardTypeInIgnoreCase(typeList, pageable);
        }
        log.info("board : {}",boards);
        List<BoardResponseDTO> list = boards.stream().map(
                board -> new BoardResponseDTO().toEntity(board)
        ).collect(Collectors.toList());
        return BoardListResponseDTO.builder()
                .value(list)
                .pageInfo(new PageResponseDTO<Board>(boards))
                .count(list.size())
                .build();
    }
    /**
     * @param boardId 상세보기할 게시판 번호
     * @return 상세보기에 필요한 게시판 정보를 반환한다
     */
    public BoardResponseDTO detail(Long boardId) {
        Board findByBoard = getFindByBoard(boardId);
        return BoardResponseDTO.builder()
                .boardId(boardId)
                .boardName(findByBoard.getBoardName())
                .boardType(findByBoard.getBoardType())
                .build();
    }
    /**
     * @param writeDTO 작성에 필요한 정보
     * @return 작성된 게시판을 반환한다
     */
    public BoardResponseDTO write(BoardRequestWriteDTO writeDTO) {
        return new BoardResponseDTO().toEntity(boardRepository.save(writeDTO.toEntity()));
    }
    /**
     * @param modifyDTO 수정에 필요한 정보
     * @return 수정된 게시판을 반환한다
     */
    public BoardResponseDTO modify(BoardRequestModifyDTO modifyDTO) {

        Board findByBoard = getFindByBoard(modifyDTO.getBoardId());

        findByBoard.setBoardName(modifyDTO.getBoardName());
        findByBoard.setBoardType(modifyDTO.getBoardType());

        return new BoardResponseDTO().toEntity(boardRepository.save(findByBoard));
    }
    /**
     * @param boardId 삭제할 게시판 번호
     */
    public void delete(Long boardId) {
        boardRepository.delete(getFindByBoard(boardId));
    }
    /**
     * @param boardId 조회할 게시판 번호
     * @return 예외처리를 포함해 게시판을 반환하다
     */
    private Board getFindByBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> {
                    throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD, boardId);
                }
        );
    }
}
```
- SpringBootTest로 요청받아 실행이 정상적으로 이루어지고 있는지 확인하였습니다.
- 주석처리로 파라미터와 반환되는 값이 무엇인지 작성해두었습니다.
- 커스텀 예외처리를 진행하였습니다.

#### ErrorCode
```
@Getter
@RequiredArgsConstructor
public enum ErrorCode implements CustomErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "❌ 파라미터 요청이 잘못 되었습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "❌ 게시판을 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "❌ 게시글을 찾을 수 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "❌ 댓글을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
```
- 예외처리를 httpStatus 400이 일어났을 경우를 커스텀 하였습니다.

<br><br>
### 데이터베이스 스키마
<img width="1174" alt="스크린샷 2023-08-14 오전 3 28 43" src="https://github.com/oslob99/mailplug-pre-assignment/assets/126937987/0aade555-b7ca-4748-a844-75e641d5a893">
### Notion API 명세서
[노션 바로가기](https://common-sheep-1e7.notion.site/API-3596a39c6a8d45f5b319aa5c4559c64e?pvs=4)

![스크린샷 2023-08-14 오전 3 30 33](https://github.com/oslob99/mailplug-pre-assignment/assets/126937987/4222437d-0ac9-48db-ba01-eb97acdbc6a0)


![스크린샷 2023-08-14 오전 3 30 48](https://github.com/oslob99/mailplug-pre-assignment/assets/126937987/f9d6c4f9-e39c-4494-9da3-9401c97e485d)

<br><br>

