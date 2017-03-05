;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;Tony Xiao Feb.23rd;;;;;;;;;;;;
;;;;;;;;;;;;;;;Part 1;;;;;;;;;;;;;;;;;;
;all-length, that takes a list and counts the number of atoms that occur in a list at all levels.
; (readable:enable-basic-curly)\
; (set-syntax-from-char #\{ #\( )
(defun all-length(lista)
  (setf res 0)
  (cond
    ((null lista) 0)
    ((atom lista) 1)
    (T (setf res (+ (all-length (car lista)) (all-length (cdr lista)))))))

;range that takes a list of numbers (with at least one element) and returns a list of length 2 of the smallest and largest numbers.
(defun range(lista)
  (list (apply 'min lista) (apply 'max lista))) ;http://www.programcreek.com/2010/10/lisp-code-find-minimum-element-in-a-list/

;; edit it 
;before that searches a list and returns a list of all elements in between the first two arguments (inclusive).
(defun before(begin end lista)
  (cond
    ((null lista) nil);empty list
    ((equal begin (car lista)) (cons begin (beforeHelp end (cdr lista))))
    ((before begin end (cdr lista)))))

(defun beforeHelp(end lista)
  (cond
    ((null lista) nil)
    ((equal end (car lista)) (list end))
    ((cons (car lista) (beforeHelp end (cdr lista))))))

;split-if that returns a list into two parts. It takes two arguments: a function (f) and a list. All members for which f is true go into one list, and the rest go into another list.
(defun split-if (func lista)
  (list (splitfalse func lista)(splitrue func lista)))

(defun splitrue(func lista)
  (cond
    ((null lista) nil)
    ((funcall func (car lista)) (cons (car lista) (splitrue func (cdr lista))))
    ((splitrue func (cdr lista)))))

(defun splitfalse(func lista)
  (cond
    ((null lista) nil)
    ((not (funcall func (car lista))) (cons (car lista) (splitfalse func (cdr lista))))
    ((splitfalse func (cdr lista)))))


;;edit here
;group that takes arguments: a list l and a number n. It returns a new list in which the elements of l are grouped into sublists of length n. The remainder is put in a final sublist.
(defun group(l n)
  (setf count(floor(/ (list-length l) n)))
  (setf begin 0)
  (setf end 0)
  (setf res '())
  (groupHelp count begin end res l n)
  )

(defun groupHelp(count begin end res l n)
  (loop for i from 1 to count
    do (setf end (+ begin n))
    do (setf res (append res (list(subseq l begin end))))
    do (setf begin (+ begin n)))
  (if (not (null (subseq l end (length l)))) ; fix here
    (setf res (append res (list (subseq l end (length l))))))
res
)

;might here
(defun mostn(f l)
  (setf res '())
  (setf num (reduce (function max)(mapcar f l)))
  (mostnHelp f l res num))

(defun mostnHelp(f l res num)
  (loop for i in l
    do (if (= num (funcall f i))
      (setf res (append res (list i)))))
  (setf res (append (list res) (list num)))
  res
  )

;;;;;;;;;;;;;;;;;;;;Part2;;;;;;;;;;;;;;;;;;
;match that compares a pattern and an assertion. 
(defun match(assertion1 assertion2)
  (cond
    ; ((and (not (null assertion1)) (not (null assertion2)))) ;1 2 not nill
    ((and (null assertion1) (not (null assertion2))) nil); 1 nill 2 not
    ((and (null assertion2) (not (null assertion1))) nil); 2 nill 1 not
    ((and (null assertion1) (null assertion2)) T) ; 1,2 nil
    ((equal (car assertion1) '?) (matchQues assertion1 assertion2)); move to next one
    ((equal (car assertion1) '!) (matchHelp assertion1 assertion2))
    (T (matchDefault assertion1 assertion2))))

(defun matchHelp (assertion1 assertion2); work for !
  (if (match (cdr assertion1) (cdr assertion2)) T (match assertion1 (cdr assertion2))))

(defun matchQues(assertion1 assertion2)
  (match (cdr assertion1) (cdr assertion2)))

(defun matchDefault(assertion1 assertion2)
  (setf facialEq (equal (car assertion1) (car assertion2)))
  (setf deepEq (match (cdr assertion1) (cdr assertion2)))
  (and facialEq deepEq))


;;;;;;;;;;;;;;;;part3;;;;;;;;;;;;;;
;match-var that extends match. The function match-var takes patterns con- taining pattern variables and matches them against assertions and assigns values to variables.

; (defun match-var(pattern assertion)
;   (cond
;     ((and (null pattern) (not (null assertion2))) nil); 1 nill 2 not
;     ((and (null assertion2) (not (null pattern))) nil); 2 nill 1 not
;     ((and (null pattern) (null assertion2)) T) ; 1,2 nil



;   )

; )
(defun match-var (l1 l2)
  (cond
    ((or (null l1) (null l2)) (equal l1 l2))
    ((and (atom (car l1)) (atom (car l2))) (and (equal (car l1) (car l2)) (match-var (cdr l1) (cdr l2))))
    ((equal (caar l1) '?) (set (cadar l1) (car l2)) (match-var (cdr l1) (cdr l2)))
    ((and (listp (car l1)) (equal (caar l1) '!)
      (cond
        ((not (boundp (cadar l1))) (set (cadar l1) (list(car l2))))
        ((listp (eval(cadar l1))) (set (cadar l1) (append (eval (cadar l1)) (list(car l2)))))
        ((set (cadar l1) (list(car l2))))
      )
    
      (match-var (cdr l1) (cdr l2)))
    )
    
    ((equal (caar l1) '!)   
      (cond ((not (boundp (cadar l1))) (set (cadar l1) (list(car l2)))))
      (match-var l1 (cdr l2))
    )
  )
)



